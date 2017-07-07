package com.thesopfactory.thesopmaker.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.thesopfactory.thesopmaker.model.Question;
import com.thesopfactory.thesopmaker.model.SOPUserInput;
import com.thesopfactory.thesopmaker.model.SopWizardQuestionSet;
import com.thesopfactory.thesopmaker.parsers.SopWizardQuestionSetParser;
import com.thesopfactory.thesopmaker.utils.SOPTemplateParser;

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
	
	private static final String OPTION_VALUE_SUBSTITUTE = "${value}"; 
			
	@Autowired
	private SOPTemplateParser sopTemplateParser;
	
	@Autowired
	private SopWizardQuestionSetParser sopWizardQuestionSetParser;
	
	public Map<Integer, String> readSOPTemplate() {
		
		log.info("The template file is at: " + sopTemplateFileLocation);
		
		File sopTemplateFile = new File(sopTemplateFileLocation);
		
		Map<Integer, String> sopTemplateMap = sopTemplateParser.readSOPTemplateSchema(sopTemplateFile);
		
		/*
		log.info("The xml content from service is: \n");
		for(Map.Entry<Integer, String> entry : sopTemplateMap.entrySet()) {
			log.info("The value is: " + entry.getKey() + " - " + entry.getValue());
		}
		*/
		
		return sopTemplateMap;
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
	public SopWizardQuestionSet readSOPWizardQuestionSetFile() throws FileNotFoundException {
		
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
	
	public SopWizardQuestionSet populateUserInputValuesInQuestionOptions( SopWizardQuestionSet sopWizardQuestionSet ) {
		
		for ( int i=0; i<sopWizardQuestionSet.getSopWizardQuestionList().size(); i++ ) {
			
			Question question = sopWizardQuestionSet.getSopWizardQuestionList().get(i);
			Set<String> options = question.getOptions();
			String[] optionsArray = (String[]) options.toArray(new String[options.size()]);
			
			question.getOptions().clear();
			
			for ( int j=0; j<optionsArray.length; j++ ) {
				String option = optionsArray[j];
				if ( option.contains(OPTION_VALUE_SUBSTITUTE) ) {
					option = option.replace(OPTION_VALUE_SUBSTITUTE , question.getUserInput());
//					optionsArray[j] = option;
					question.getOptions().add(option);
				}
			}
			
		}
		
		System.out.println("The set contains: \n" + sopWizardQuestionSet.getSopWizardQuestionList().size() );
		
		for ( Question question : sopWizardQuestionSet.getSopWizardQuestionList() ) {
			System.out.println(question.getOptions());
		}
		
		return sopWizardQuestionSet;
	}
	
}
