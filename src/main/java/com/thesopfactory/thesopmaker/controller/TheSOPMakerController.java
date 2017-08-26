package com.thesopfactory.thesopmaker.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.thesopfactory.thesopmaker.model.Department;
import com.thesopfactory.thesopmaker.model.Question;
import com.thesopfactory.thesopmaker.service.SOPPDFGenerationService;
import com.thesopfactory.thesopmaker.service.TheSOPMakerService;

/**
Author- Nikhil
*/

@RestController
@RequestMapping("sopwizard")
public class TheSOPMakerController {

	private static final Logger log = LoggerFactory.getLogger(TheSOPMakerController.class);
	
	@Autowired
	private TheSOPMakerService theSopMakerService;
	
	@Autowired
	private SOPPDFGenerationService sopPDFGenerationService;
	
	@Value("${sop.storage.directory}")
	private String sopStorageDirectory;
	
	@GetMapping("/getAllDepartments")
	public List<Department> getAllDepartments() {
		
		return theSopMakerService.getAllDepartments();
	}
	
	@GetMapping("/getQuestionSetForPagename")
	public List<Question> getQuestionSetForPage( String pageName, String departmentName ) {
		
		log.info( "Retrieving questions for Page: {} and Department name: {}", pageName, departmentName );
		return theSopMakerService.retrieveQuestionsForPage( pageName, departmentName );
	}

	@RequestMapping( value="/loadSopPreviewPdf", method=RequestMethod.POST, produces="application/pdf")
	public ResponseEntity<byte[]> loadSopPreviewPdf( @RequestBody List<Question> questions ) throws FileNotFoundException, DocumentException {

		String sopPDFOutputFilename = sopPDFGenerationService.generateSOPPDFOutputFilename( questions.get( 0 ) );
		sopPDFGenerationService.generateSopPdfFile( sopStorageDirectory + sopPDFOutputFilename, questions );
		
		try {
			return theSopMakerService.loadSopPreviewPdf( sopStorageDirectory, sopPDFOutputFilename );
		} catch (IOException e) {
			log.error( "IOException occured: {}", e.getMessage() );
			throw new IllegalArgumentException( e.getMessage() );
		}
	}
}
