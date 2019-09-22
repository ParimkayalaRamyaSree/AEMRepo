package com.adobe.aem.guides.wknd.core.models;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.api.resource.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.osgi.service.cm.ConfigurationAdmin;

import com.google.gson.Gson;

import org.osgi.service.cm.Configuration;


/**
 * The Class AccordionModel.
 */
@Model(adaptables = Resource.class)

public class TestMultiField {
	
	private List<TestMultiField> sampleMultiList = new ArrayList<TestMultiField>();
	
	private List<String> multifieldList;
	
	@Inject
	@Optional
	private String[] multiList;
	
	
	@Inject
	@Optional
	private String title;
	
	
	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	@Inject
	@Optional
	private String description;
	
	/**
	* Init the.
	 * @throws IOException 
	*/
	@PostConstruct
	protected void init() throws IOException {
		System.out.println("init method");
		if (ArrayUtils.isNotEmpty(multiList)) {
			multifieldList = Arrays.asList(multiList);	
			multifieldList.forEach(e -> sampleMultiList.add(new Gson().fromJson(e, TestMultiField.class)));
		}
	}
	
	
}
