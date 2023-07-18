package com.iapps.xmltodb.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.iapps.xmltodb.dto.EpaperDto;
import com.iapps.xmltodb.model.Epaper;

import lombok.extern.java.Log;

@Component
@Log
public class EpaperConverter {
	public static EpaperDto convert(Epaper epaper) {
		log.info("uploadXmlToDB -> convert Epaper to EpaperDto");
	    return  EpaperDto.builder()
                .newsPaperName(epaper.getNewsPaperName())
                .height(epaper.getHeight())
                .width(epaper.getWidth())
                .dpi(epaper.getDpi())
                .createdAt(epaper.getCreatedAt())
                .uploadedAt(epaper.getUploadedAt())
                .fileName(epaper.getFileName())
                .build();
	}
	
	
	public boolean isValid(String xsdPath, String xmlPath) throws IOException, SAXException {
		Validator validator = initValidator(xsdPath);
		try {
			validator.validate(new StreamSource(getFile(xmlPath)));
			return true;
		} catch (SAXException e) {
			return false;
		}
	}
	
	public boolean isValid(String xsdPath, Reader xmlFile) throws IOException, SAXException {
		Validator validator = initValidator(xsdPath);
		try {
			validator.validate(new StreamSource(xmlFile));
			return true;
		} catch (SAXException e) {
			return false;
		}
	}
	
	private Validator initValidator(String xsdPath) throws SAXException {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Source schemaFile = new StreamSource(getFile(xsdPath));
		Schema schema = factory.newSchema(schemaFile);
		return schema.newValidator();
	}
	
	private File getFile(String fileName) {
		return new File(getClass().getClassLoader().getResource(fileName).getFile());
	}
}
