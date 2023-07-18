package com.iapps.xmltodb.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.iapps.xmltodb.dto.EpaperDto;
import com.iapps.xmltodb.exception.InvalidXMLException;
import com.iapps.xmltodb.model.Epaper;
import com.iapps.xmltodb.repository.EpaperRepository;
import com.iapps.xmltodb.service.EpaperService;
import com.iapps.xmltodb.util.EpaperConverter;
import com.iapps.xmltodb.xmlobject.EpaperRequest;

import lombok.extern.java.Log;

/**
 * 
 *
 */
@Service
@Transactional
@Log
public class EpaperServiceImpl implements EpaperService {

	@Autowired
	private EpaperRepository epaperRepository;
	
	@Autowired
	private EpaperConverter converter;
	
	@Override
	public EpaperDto uploadXmlToDB(MultipartFile file) throws InvalidXMLException {
		log.info("uploadXmlToDB -> Unmarshalling file and saving");
		Epaper epaper = new Epaper();
		try {
			if (file.getContentType().contains("text/xml") || file.getContentType().contains("application/xml")) {
				Reader reader = new InputStreamReader(file.getInputStream());
				if(converter.isValid("schema/ePaperRequest.xsd", reader)){
					JAXBContext jaxbContext = JAXBContext.newInstance(EpaperRequest.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					EpaperRequest epaperRequest = (EpaperRequest) jaxbUnmarshaller.unmarshal(file.getInputStream());
					epaper = Epaper.builder().newsPaperName(epaperRequest.getDeviceInfo().getAppInfo().getNewspaperName())
							.height(epaperRequest.getDeviceInfo().getScreenInfo().getHeight())
							.width(epaperRequest.getDeviceInfo().getScreenInfo().getWidth())
							.dpi(epaperRequest.getDeviceInfo().getScreenInfo().getDpi()).createdAt(Instant.now())
							.uploadedAt(Instant.now()).fileName(file.getName()).build();
				}else {
					throw new InvalidXMLException("Invalid XML");
				}
			} else {
				throw new InvalidXMLException("Invalid XML");
			}
		} catch (JAXBException | IOException | SAXException e) {
			throw new InvalidXMLException("Invalid XML");
		}
		return EpaperConverter.convert(epaperRepository.save(epaper));
	}

	/**
	 * 
	 */
	@Override
	public Page<EpaperDto> getEpaperList(Integer pageNo, Integer pageSize, String sortBy, Boolean asc, String newsPaperName)
			throws Exception {
		Pageable pageable = PageRequest.of(pageNo == null ? 0 : pageNo,
				pageSize == null ? (Integer.MAX_VALUE - 1) : pageSize == Integer.MAX_VALUE ? (pageSize - 1) : pageSize,
				Sort.by(asc == null || asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy == null ? "id" : sortBy));
		return epaperRepository.findAll((root, quert, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (newsPaperName != null && !newsPaperName.isEmpty()) {
			    predicates.add(cb.like(cb.lower(root.get("newsPaperName")),
                        "%" + newsPaperName.toLowerCase() + "%"));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		}, pageable).map(EpaperConverter::convert);
	}
}
