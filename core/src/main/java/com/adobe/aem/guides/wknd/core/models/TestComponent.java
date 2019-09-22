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
import org.osgi.service.cm.Configuration;
import com.google.gson.Gson;


/**
 * The Class AccordionModel.
 */
@Model(adaptables = Resource.class)

public class TestComponent {
	
	private List<TestMultiField> sampleMultiList = new ArrayList<TestMultiField>();
	
	private List<String> multifieldList;
	
	@Inject
	@Optional
	private String[] multiList;
	
	
	@Inject
	@Optional
	private String text;
	
	@Inject
	private ConfigurationAdmin configAdmin;
	
	public String getText() {
		return text;
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
				
	/*	Configuration conf = configAdmin.getConfiguration("wknd.test.config");

		String text = conf.getProperties().get("simple") == null
		? "text"
		: conf.getProperties().get("simple").toString();

		System.out.println("configuration admin is :"+text);
*/
		if (ArrayUtils.isNotEmpty(multiList)) {
			multifieldList = Arrays.asList(multiList);	
			multifieldList.forEach(e -> sampleMultiList.add(new Gson().fromJson(e, TestMultiField.class)));
		}
	}
	
	
}
