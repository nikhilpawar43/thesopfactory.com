package com.thesopfactory.thesopmaker.parsers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.thesopfactory.thesopmaker.model.Option;
import com.thesopfactory.thesopmaker.model.Question;
import com.thesopfactory.thesopmaker.model.SopWizardQuestionSet;

/**
Author- Nikhil
*/

@Component
public class SopWizardQuestionSetParser extends DefaultHandler {
	
	private static final Logger log = LoggerFactory.getLogger(SopWizardQuestionSetParser.class);

	private static final String SOP_QUESTION_SET 	= "sop_question_set";
	private static final String PAGE	 			= "page";
	private static final String QUESTIONS 			= "questions";
	private static final String QUESTION 			= "question";
	private static final String OPTION 				= "option";
	private static final String ID					= "id";
	
	private boolean sopQuestionSetFlag 	= false;
	private boolean pageFlag 			= false;
	private boolean questionsFlag 		= false;
	private boolean questionFlag 		= false;
	private boolean optionFlag 			= false;
	
	private Question question;
	private List<Question> sopWizardQuestionList;
	private List<Option> options;
	private SopWizardQuestionSet sopWizardQuestionSet;
	private String pageValue;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if ( qName.equals(SOP_QUESTION_SET) ) {
			log.debug("SOP Wizard Question Set File parsing initiated.");
			sopWizardQuestionSet = new SopWizardQuestionSet();
			sopQuestionSetFlag = true;
		} else if ( qName.equals(PAGE) ) {
			pageValue = attributes.getValue("value");
			pageFlag = true;
		} else if ( qName.equals(QUESTIONS) ) {
			sopWizardQuestionList = new ArrayList<>();
			questionsFlag = true;
		} else if ( qName.equals(QUESTION) ) { 
			questionFlag = true;
			question = new Question();
			question.setPage(pageValue);
			question.setId( Long.parseLong( attributes.getValue(ID) ) );
//			question.setValue(attributes.getValue("value") );
			options = new ArrayList<>();
		} else if ( qName.equals(OPTION) ) {
			optionFlag = true;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if ( qName.equals(QUESTION) ) { 
			sopWizardQuestionList.add(question);
		} else if ( qName.equals(SOP_QUESTION_SET) ) {
			sopWizardQuestionSet.setSopWizardQuestionList(sopWizardQuestionList);
			log.debug("SOP Wizard Question Set File parsing completed successfully.");
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		if ( sopQuestionSetFlag ) {
			sopQuestionSetFlag = false;
		} else if ( pageFlag ) {
			pageFlag = false;
		} else if ( questionsFlag ) {
			questionsFlag = false;
		} else if ( questionFlag ) { 
			questionFlag = false;
		} else if ( optionFlag ) {
//			options.add(new String(ch, start, length).trim());
			optionFlag = false;
		}
	}

	public SopWizardQuestionSet getSopWizardQuestionSet() {
		return sopWizardQuestionSet;
	}
}
