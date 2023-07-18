package com.iapps.xmltodb;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import com.iapps.xmltodb.util.EpaperConverter;

@SpringBootTest
class XmLtoDbTests {

	@Autowired
	private EpaperConverter epaperConverter;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void validXml_ThenTrue() throws IOException, SAXException {
		Assert.assertTrue(epaperConverter.isValid("epaperRequest.xsd", "EpaperRequest.xml"));
	}

	@Test
	public void invalidXML_ThenFalse() throws IOException, SAXException {
		Assert.assertFalse(epaperConverter.isValid("epaperRequest.xsd", "EpaperRequestInvalid.xml"));
	}
}
