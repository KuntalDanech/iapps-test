package com.iapps.xmltodb.exception.advicer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.iapps.xmltodb.exception.InvalidXMLException;

import lombok.extern.java.Log;

@RestControllerAdvice
@Log
public class EpaperControllerAdvice {
	
	@ExceptionHandler(InvalidXMLException.class)
	public ResponseEntity<Object> invalidXmlFile(Exception ex){
		log.log(Level.SEVERE,ex.getMessage(), ex);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("status", false);
		map.put("code", 400);
		map.put("message", "XML file is not valid");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	}
	
}
