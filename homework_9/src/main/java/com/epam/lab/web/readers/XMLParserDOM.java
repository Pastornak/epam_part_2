package main.java.com.epam.lab.web.readers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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

	public List<String> getAttributes(String attributeName){
		NodeList list = doc.getElementsByTagName(attributeName);
		List<String> result = new LinkedList<>();
		for(int i = 0; i < list.getLength(); i++){
			result.add(list.item(i).getTextContent().trim());
		}
		return result;
	}
}
