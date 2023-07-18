package com.iapps.xmltodb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.iapps.xmltodb.model.Epaper;

/**
 * 
 *
 */
public interface EpaperRepository extends JpaRepository<Epaper, Long>, JpaSpecificationExecutor<Epaper> {

	Page<Epaper> findByNewsPaperNameLikeIgnoreCase(Pageable pageable, String newsPaperName);
	
	Page<Epaper> findAll(Specification<Epaper> spec, Pageable pageable);
}
