package com.iapps.xmltodb.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpaperDto {

	private String newsPaperName;
	private float height;
	private float width;
	private int dpi;
	private Instant createdAt;
	private Instant uploadedAt;
	private String fileName;
	
}
