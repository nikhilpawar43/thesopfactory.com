package com.thesopfactory.thesopmaker.controller;

import java.io.FileNotFoundException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thesopfactory.thesopmaker.model.SOPUserInput;
import com.thesopfactory.thesopmaker.model.SopWizardQuestionSet;
import com.thesopfactory.thesopmaker.service.TheSOPMakerService;

/**
Author- Nikhil
*/

@RestController
public class TheSOPMakerController {

	private static final Logger log = LoggerFactory.getLogger(TheSOPMakerController.class);
	
	@Autowired
	private TheSOPMakerService theSopMakerService;
	
	@PostMapping("/createSOP")
	public Map<Integer, String> createSOP( @RequestBody SOPUserInput sopUserInput) {
		
		log.info("The user input is: " + sopUserInput);
		
		Map<Integer, String> sopTemplateMap = theSopMakerService.readSOPTemplate();
		sopTemplateMap = theSopMakerService.substituteUserInputIntoSOPTemplate(sopTemplateMap, sopUserInput);
		
		return sopTemplateMap;
	}
	
	@GetMapping("/getQuestionSetForPage")
	public SopWizardQuestionSet getQuestionSetForPage() {
		
		SopWizardQuestionSet sopWizardQuestionSet = null;	
		try {
			sopWizardQuestionSet = theSopMakerService.readSOPWizardQuestionSetFile();
		} catch (FileNotFoundException e) {
			log.error("An exception occured while reading the input file: " + e.getMessage());
		}
		
		return sopWizardQuestionSet;
		
	}
	
	@PostMapping("/getQuestionSetWithUserInput")
	public SopWizardQuestionSet getQuestionSetWithUserInput( @RequestBody SopWizardQuestionSet sopWizardQuestionSet) {
		
		/*	Collect the user input the substitute it in the question's options.		*/
		
		return theSopMakerService.populateUserInputValuesInQuestionOptions(sopWizardQuestionSet);
	}
}
