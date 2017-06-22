package com.thesopfactory.thesopmaker.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
Author- Nikhil
*/

@Component
public class SOPTemplateParser extends DefaultHandler {

	private static final Logger log = LoggerFactory.getLogger(SOPTemplateParser.class);
	
	private boolean bcontent;
	
	private int activeFlag;
	
	private int sequenceNumber;
	
	private Map<Integer, String> sopTemplateMap;
	
	public Map<Integer, String> readSOPTemplateSchema(File sopTemplateFile) {
		
		sopTemplateMap = new TreeMap<>();
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			saxParser.parse(sopTemplateFile, this);
			
		} catch (SAXException e) {
			log.error("There was a SAX Parser exception: " + e.getMessage());
		} catch (ParserConfigurationException e) {
			log.error("There was a Parser Configuration exception: " + e.getMessage());
		} catch (IOException e) {
			log.error("There was an IO Exception: " + e.getMessage());
		}
		
		return sopTemplateMap;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if (qName.equalsIgnoreCase("section")) {
			activeFlag = Integer.parseInt(attributes.getValue("active"));
			sequenceNumber = Integer.parseInt(attributes.getValue("sequence"));
		} else if (qName.equalsIgnoreCase("content")) {
			bcontent = true;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
	
		if (qName.equalsIgnoreCase("section")) {

		} else if (qName.equalsIgnoreCase("sop-document")) {
			/*
			log.info("The xml content is: \n");
			for(Map.Entry<Integer, String> entry : sopTemplateMap.entrySet()) {
				log.info("The value is: " + entry.getKey() + " - " + entry.getValue());
			}
			*/
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		if (bcontent && activeFlag == 1) {
			sopTemplateMap.put(sequenceNumber, new String(ch, start, length).trim());
			bcontent = false;
		}
	}
	
}
