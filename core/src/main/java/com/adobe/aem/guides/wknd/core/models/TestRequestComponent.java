package com.adobe.aem.guides.wknd.core.models;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.servlets.SubmitResourceServlet;


/**
 * 
 * 
 * The Class AccordionModel.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class TestRequestComponent {
	private static final Logger LOG = LoggerFactory.getLogger(TestRequestComponent.class);  
	
	@Inject
	private ResourceResolverFactory resourceResolverFactory;
	
	@Inject
	@Optional
	@Via("resource")
	private String text;


	public String getText() {
		return text;
	}

	public String getDescription() {
		return description;
	}

	@Inject
	@Optional
	@Via("resource")
	private String description;

	@Inject
	private ConfigurationAdmin configAdmin;
	
	/**
	* Inits the.
	 * @throws IOException 
	 * @throws LoginException 
	 * @throws RepositoryException 
	 * @throws ConstraintViolationException 
	 * @throws LockException 
	 * @throws VersionException 
	 * @throws ValueFormatException 
	*/
	@PostConstruct
	protected void init() throws IOException, LoginException, ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException {
	
	LOG.info("init method");
	LOG.info("resourceResolverFactory"+resourceResolverFactory.toString());
	
	ResourceResolver resolver = null;
	Session session = null;

	Map<String, Object> authInfoParam = new HashMap<String, Object>();
	authInfoParam.put(ResourceResolverFactory.SUBSERVICE, "writesvc");
	LOG.info("authInfoParam="+authInfoParam.toString());
	resolver = resourceResolverFactory.getServiceResourceResolver(authInfoParam);
	LOG.info("Before session");
	session = resolver.adaptTo(Session.class);
	LOG.info("After session");
	Node node = session.getNode("/content/wknd/en/homepage/jcr:content");

	node.setProperty("name", "ramya");
	session.save();

	
	/*Configuration conf = configAdmin.getConfiguration("wknd.test.config");

	String text = conf.getProperties().get("simple") == null
	? "text"
	: conf.getProperties().get("simple").toString();

	System.out.println("configuration admin is :"+text);
*/
	/*if (ArrayUtils.isNotEmpty(sampleList)) {
	multifieldList = Arrays.asList(sampleList);	
	multifieldList.forEach(e -> sampleLinkList.add(new Gson().fromJson(e, SamplePojo.class)));
	}*/

	}
}
