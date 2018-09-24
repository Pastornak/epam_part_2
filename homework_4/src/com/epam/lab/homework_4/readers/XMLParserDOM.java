package com.epam.lab.homework_4.readers;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XMLParserDOM {
	
	private Document doc;
	
	public XMLParserDOM(String pathToFile){
		try{
		File inputFile = new File(pathToFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(inputFile);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getAttribute(String attributeName){
		return doc.getElementsByTagName(attributeName).item(0).getTextContent().trim();
	}

}
