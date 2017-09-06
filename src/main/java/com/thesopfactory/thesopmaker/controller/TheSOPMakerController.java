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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.thesopfactory.thesopmaker.model.Department;
import com.thesopfactory.thesopmaker.model.Question;
import com.thesopfactory.thesopmaker.model.SOPDetail;
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
	
	@RequestMapping( value="/getQuestionSetForPagename", method=RequestMethod.GET, produces="application/json")
	public List<Question> getQuestionSetForPage( String pageName, long departmentId ) {
		
		log.info( "Retrieving questions for Page: {} and Department ID: {}", pageName, departmentId );
		return theSopMakerService.retrieveQuestionsForPage( pageName, departmentId );
	}
	
	@RequestMapping( value="/sendQuestionsWithUserInput", method=RequestMethod.POST )
	public ResponseEntity<List<SOPDetail>> sendQuestionsWithUserInput( @RequestBody List<Question> questions ) {
		
		return theSopMakerService.sendQuestionsWithUserInput( questions );
	}
	
	@PostMapping("/setDepartmentForSop")
	public ResponseEntity<SOPDetail> setDepartmentForSop( @RequestBody Question question ) {
		
		return theSopMakerService.setDepartmentForSop( question );
	}

	@RequestMapping( value="/loadSopPreviewPdf", method=RequestMethod.GET, produces="application/pdf")
	public ResponseEntity<byte[]> loadSopPreviewPdf() throws FileNotFoundException, DocumentException {

		List<SOPDetail> sopDetails = theSopMakerService.getSopDetails();
		
		if (sopDetails != null & sopDetails.size() > 2 ) {
			
			String sopPDFOutputFilename = sopPDFGenerationService.generateSOPPDFOutputFilename( sopDetails.get( 1 ).getQuestion() );
			sopPDFGenerationService.generateSopPdfFile( sopStorageDirectory + sopPDFOutputFilename, sopDetails );
			
			try {
				return theSopMakerService.loadSopPreviewPdf( sopStorageDirectory, sopPDFOutputFilename );
			} catch (IOException e) {
				log.error( "IOException occured: {}", e.getMessage() );
				throw new IllegalArgumentException( e.getMessage() );
			}
		} else {
			throw new IllegalStateException("The sopDetails is not having the first 2 questions: 1. Department 2. Candidate name");
		}
		
	}
	
}
