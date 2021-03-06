package com.thesopfactory.thesopmaker.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.thesopfactory.thesopmaker.dao.DepartmentDao;
import com.thesopfactory.thesopmaker.dao.OptionDao;
import com.thesopfactory.thesopmaker.dao.QuestionDao;
import com.thesopfactory.thesopmaker.model.Department;
import com.thesopfactory.thesopmaker.model.Option;
import com.thesopfactory.thesopmaker.model.Question;
import com.thesopfactory.thesopmaker.model.SOP;
import com.thesopfactory.thesopmaker.model.SOPDetail;
import com.thesopfactory.thesopmaker.model.SOPUserInput;
import com.thesopfactory.thesopmaker.model.SopWizardQuestionSet;
import com.thesopfactory.thesopmaker.parsers.SopWizardQuestionSetParser;

/**
Author- Nikhil
*/

@Service
public class TheSOPMakerService {

	private static final Logger log = LoggerFactory.getLogger(TheSOPMakerService.class);
	
	@Value("${sop.template.file.location}")
	private String sopTemplateFileLocation;
	
	@Value("${sop.wizard.questionset.file.location}")
	private String sopWizardQuestionSetFileLocation;
	
	@Value("${freemarker-template-basepackage}")
	private String freemarkerTemplateBasepackage;
	
	@Autowired
	private SopWizardQuestionSetParser sopWizardQuestionSetParser;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private OptionDao optionDao;
	
	private SOP sop;
	
	private List<SOPDetail> sopDetails;
	
	public TheSOPMakerService() {
		
	}
	
	public Map<Integer, String> substituteUserInputIntoSOPTemplate(Map<Integer, String> sopTemplateMap, SOPUserInput sopUserInput) {
		
		String attribute;
		String value;
		String content;
		int key;
		
		Map<String, String> sopUserInputMap = getUserInputAttributes(sopUserInput.toString());
		/*	{hobbies=Watching BB ki vines, name=Nikhil Pawar, degree=Bachelor in Engineering, company=ADP Pvt Ltd, age=27}		*/
		
		for (Map.Entry<String, String> entry : sopUserInputMap.entrySet()) {
			attribute = entry.getKey();
			value = entry.getValue();
		
			for (Map.Entry<Integer, String> sopContent : sopTemplateMap.entrySet()) {
				key = sopContent.getKey();
				content = sopContent.getValue();
				
				if (content.contains("${" + attribute + "}")) {
					content = content.replace("${" + attribute + "}", value);
					sopTemplateMap.put(key, content);
					break;
				}
			}
		}
		
		return sopTemplateMap;
	}
	
	public Map<String, String> getUserInputAttributes(String sopUserInput) {
		
		/*	The object in string format is: SOPUserInput [name=Nikhil Pawar, age=27, degree=Bachelor in Engineering, company=ADP Pvt Ltd, hobbies=Watching BB ki vines]		*/		
		Map<String, String> sopUserInputMap = new HashMap<>();
		
		sopUserInput = sopUserInput.substring(14, sopUserInput.length()-1);
		
		String[] sopUserInputattributes = sopUserInput.split(",");
		
		for (String attribute : sopUserInputattributes) {
			String[] values = attribute.trim().split("=");

			if (values.length > 1) {
				sopUserInputMap.put(values[0], values[1]);
			}
		}
		
		return sopUserInputMap;
	}
	
	/**
	 * 1. This function reads the provided input XML file. 
	 * 2. Parses the file using SopWizardQuestionSetParser SAX parser.
	 * 3. returns SopWizardQuestionSet object containing list of questions.
	 * @return SopWizardQuestionSet
	 * @throws FileNotFoundException
	 */
	public SopWizardQuestionSet readSOPWizardQuestionSetFile( String pageName ) throws FileNotFoundException {
		
		InputStream inputStream = this.loadXMLInputFile( sopWizardQuestionSetFileLocation );
		
		this.parseFile(sopWizardQuestionSetParser, inputStream);
		
		return sopWizardQuestionSetParser.getSopWizardQuestionSet();
	}
	
	/**
	 * This function accepts the input stream and the SopWizardQuestionSetParser, and initiates the parsing process.
	 * @param parserImpl
	 * @param inputStream
	 */
	public void parseFile( DefaultHandler parserImpl, InputStream inputStream ) {
		
		try {
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			saxParser.parse(inputStream, parserImpl);
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function will locate the provide xml input file, and return the input stream pointing to the file.
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 */
	public InputStream loadXMLInputFile( String filename ) throws FileNotFoundException {
		
		return new FileInputStream(filename);
		
	}
	
	public List<Department> getAllDepartments() {
		
		return departmentDao.getAllDepartments();
	}
	
	/**
	 * 2nd set of code to retrieve tab wise questions.
	 * The functions written above will be removed down the line.
	 */
	
	public List<Question> retrieveQuestionsForPage( String pageName, long departmentId ) {
		
		List<Question> questions = null ;
		
		if ( departmentId != 0 ) {

			questions = new ArrayList<>();
			
			if ( pageName.equals("Department") ) {
				if ( this.sopDetails != null ) {
					SOPDetail sopDetail = this.sopDetails.get( 0 );
					Question question = sopDetail.getQuestion();
					questions.add( question );
				} else {
					log.error("SopDetails list is empty or null !");
					throw new IllegalStateException("SopDetails list is empty or null !");
				}
			} else {
				if ( this.sopDetails != null ) {
					for ( SOPDetail sopDetail : this.sopDetails) {
						Question question = sopDetail.getQuestion();
						if ( question.getPage().equals( pageName ) ) {
							questions.add( question );
						}
					}
					
					if ( questions.size() == 0 ) {
						Department department = departmentDao.getDepartmentById( departmentId );
						questions = questionDao.getQuestionsListByPagenameAndDepartment(pageName, department.getId());
						
						for (Question question : questions) {
							List<Option> options = optionDao.getOptionsByQuestion( question.getId() );
							question.setOptions( options );
						}
					}
				} else {
					log.error("SopDetails list is empty or null !");
					throw new IllegalStateException("SopDetails list is empty or null !");
				}
			}
		} else if ( departmentId == 0 ) {
			
			if ( pageName.equals("Department") ) {
				
				questions = questionDao.getQuestionsListByPagename(pageName);
				List<Department> departments = departmentDao.getAllDepartments();
				List<Option> options = new ArrayList<Option>();
				
				for (Department department : departments) {
					Option option = new Option();
					option.setId( department.getId() );
					option.setValue( department.getName() );
					options.add( option );
					questions.get(0).setOptions( options );
				}
			}
		}
		
		return questions;
	}
	
	public ResponseEntity<SOPDetail> setDepartmentForSop( Question question ) {
		
		log.info( "Create sop for department ID: " + question.getUserOption() );
		 
		sop = new SOP();
		this.sopDetails = new ArrayList<>();
		
		SOPDetail sopDetail = new SOPDetail();
		sopDetail.setSop( sop );
		sopDetail.setQuestion( question );
		sopDetail.setUserOptionId( question.getUserOption() );
		
		this.sopDetails.add( sopDetail );
		
		ResponseEntity<SOPDetail> response = new ResponseEntity<SOPDetail>( sopDetail, HttpStatus.CREATED );
		return response;
	}
	
	public ResponseEntity<List<SOPDetail>> sendQuestionsWithUserInput( List<Question> questions ) {
		
		List<Question> sopDetailQuestionsList = new ArrayList<>();
		
		for ( SOPDetail sopDetail : sopDetails ) {
			sopDetailQuestionsList.add( sopDetail.getQuestion() );
		}
		
		for ( Question question : questions ) {
			
			SOPDetail sopDetail = null;
			
			if ( sopDetailQuestionsList.contains( question ) ) {
				
				sopDetail = sopDetails.get( sopDetailQuestionsList.indexOf( question ) );
				sopDetail.setSop( sop );
				sopDetail.setQuestion( question );
				sopDetail.setUserInput( question.getUserInput() );
				sopDetail.setUserOptionId( question.getUserOption() );
				
			} else {
				
				sopDetail = new SOPDetail();
				sopDetail.setSop( sop );
				sopDetail.setQuestion( question );
				sopDetail.setUserInput( question.getUserInput() );
				sopDetail.setUserOptionId( question.getUserOption() );
				sopDetails.add( sopDetail );
				
			}
		}
		
		sopDetailQuestionsList.clear();
		
		ResponseEntity<List<SOPDetail>> response = new ResponseEntity<List<SOPDetail>>( sopDetails, HttpStatus.CREATED );
				
		return response;
	}
	
	public ResponseEntity<byte[]> loadSopPreviewPdf( String sopStorageDirectory, String sopPDFOutputFilename ) throws IOException {
		
		File sopPDFFile = new File( sopStorageDirectory + sopPDFOutputFilename ); 
		FileInputStream inputStream = new FileInputStream( sopPDFFile );
		byte[] contents = IOUtils.toByteArray( inputStream );
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.setContentDispositionFormData( "filename", sopPDFOutputFilename );
		
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>( contents, headers, HttpStatus.OK );
		
		return response;
	}

	public SOP getSop() {	return sop;	  }

	public List<SOPDetail> getSopDetails() {	return sopDetails;	  }
	
}
