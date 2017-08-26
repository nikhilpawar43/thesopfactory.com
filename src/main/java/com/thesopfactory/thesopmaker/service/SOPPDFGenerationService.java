package com.thesopfactory.thesopmaker.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.thesopfactory.thesopmaker.model.Question;

/**
Author- Nikhil
Date - Aug 19, 2017
*/
@Service
public class SOPPDFGenerationService {
	
	private static final Font HEADER_FONT = new Font( Font.TIMES_ROMAN, 18, Font.BOLD );
	private static final Font PARAGRAPH_FONT = new Font( Font.HELVETICA, 12 );
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat( "dd-MMM-yyyy_hh-mm-ss" );
	
	public String generateSOPPDFOutputFilename( Question question ) {
		
		Timestamp timeStamp = new Timestamp( System.currentTimeMillis() );
		
		StringBuilder sopPDFOutputFilename = new StringBuilder(); 
		sopPDFOutputFilename.append( question.getUserInput() );
		sopPDFOutputFilename.append( "-" );
		sopPDFOutputFilename.append( DATE_FORMAT.format(timeStamp) );
		sopPDFOutputFilename.append( ".pdf" );
		
		return sopPDFOutputFilename.toString();
	}

	public Document generateSopPdfFile ( String sopPDFFile, List<Question> questions ) throws FileNotFoundException, DocumentException {
		
		Document document = new Document();
		PdfWriter.getInstance( document, new FileOutputStream( sopPDFFile ) );
		document.open();
		document = addMetaData( document, questions.get( 0 ) );
		document = addContent( document, questions );
		document.close();
		return document;
	}
	
	private Document addMetaData( Document document, Question question ) {
		
		document.addTitle( question.getUserInput() );
		document.addSubject( question.getUserInput() + " SOP");
		document.addCreator("thesopmaker.com");
		document.addAuthor("thesopmaker.com");
		
		return document;
	}
	
	private Document addContent( Document document, List<Question> questions ) throws DocumentException {
		
		Paragraph sop = new Paragraph();
		sop.add( new Paragraph( questions.get( 0 ).getUserInput(), HEADER_FONT ) );
		sop.setAlignment( Element.ALIGN_CENTER);
		addEmptyLine( sop, 1 );
		
		document.add( sop );
		
		for( Question question : questions ) {
			
			Paragraph paragraph = new Paragraph( question.getUserOptionId().getValue(), PARAGRAPH_FONT );
			paragraph.setAlignment( Element.ALIGN_JUSTIFIED );
			document.add( paragraph );
		}
		
		return document;
	}
	
	private Paragraph addEmptyLine( Paragraph paragraph, int number ) {
		for( int i=0; i<number; i++ ) 
			paragraph.add( new Paragraph(" ") );
		
		return paragraph;
	}
}
