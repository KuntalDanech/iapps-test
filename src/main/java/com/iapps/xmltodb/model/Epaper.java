package com.iapps.xmltodb.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 *
 */
@Entity
@Table(name = "epaperrequest")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Epaper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String newsPaperName;
	private float height;
	private float width;
	private int dpi;
	private Instant createdAt;
	private Instant uploadedAt;
	private String fileName;
}
