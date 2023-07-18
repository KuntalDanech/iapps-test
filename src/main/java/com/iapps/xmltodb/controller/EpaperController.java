package com.iapps.xmltodb.controller;

import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iapps.xmltodb.dto.EpaperDto;
import com.iapps.xmltodb.exception.InvalidXMLException;
import com.iapps.xmltodb.service.EpaperService;

import lombok.extern.java.Log;

/**
 * 
 */
@RestController
@Log
@RequestMapping("/api")
public class EpaperController {

	@Autowired
	private EpaperService epaperService;

	/**
	 * 
	 * @param file
	 * @return ResponseEntity
	 * @throws InvalidXMLException 
	 */
	@PostMapping("/upload-file")
	public ResponseEntity<Object> uploadFile(@RequestParam MultipartFile file) throws InvalidXMLException {
		log.info("Upload File" + file.getName());
		var epaperDto = epaperService.uploadXmlToDB(file);
		return ResponseEntity.status(HttpStatus.OK).body(epaperDto);
	}

	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * @param asc
	 * @param search
	 * @return
	 */
	@GetMapping("/epaperList")
	public ResponseEntity<Object> getEpaperList(
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "asc", defaultValue = "false", required = false) Boolean asc,
			@RequestParam(value = "search", defaultValue = "", required = false) String search) {
		log.info("get Files");
		try {
			Page<EpaperDto> pages = epaperService.getEpaperList(pageNo, pageSize, sortBy, asc, search);
			return ResponseEntity.status(HttpStatus.OK).body(pages);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
