package com.thesopfactory.thesopmaker.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.thesopfactory.thesopmaker.model.SOPUserInput;
import com.thesopfactory.thesopmaker.utils.SOPTemplateParser;

/**
Author- Nikhil
*/

@Service
public class TheSOPMakerService {

	private static final Logger log = LoggerFactory.getLogger(TheSOPMakerService.class);
	
	@Value("${sop.template.file.location}")
	private String sopTemplateFileLocation;
	
	@Autowired
	private SOPTemplateParser sopTemplateParser;
	
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
	
}
