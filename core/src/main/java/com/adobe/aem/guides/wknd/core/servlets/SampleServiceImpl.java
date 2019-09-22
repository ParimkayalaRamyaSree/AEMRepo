package com.adobe.aem.guides.wknd.core.servlets;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, metatype = true, label = "Sample - Utility Service", description = "Provide an efficient way access features such as reading a config property")

@Service(value = ISample.class)

public class SampleServiceImpl implements ISample {

	@Reference
	private ResourceResolverFactory resolverFactory;

	private static final String DEFAULT_IMG_PATH = "/content/dam/rmit-ui/eventstack/defaultimage.jpg";

	private static final Logger LOG = LoggerFactory.getLogger(SampleServiceImpl.class);

	@Property(label = "Event Stack List Item Default Image Path", description = "Eg: /content/dam/rmit-ui/eventstack/defaultimage.jpg", value = DEFAULT_IMG_PATH)

	private static final String OSGI_CONFIG_FILE_PATH = "/content/dam/rmit-ui/eventstack/defaultimage.jpg";
	private String defaultImagePath;
	ResourceResolver resourceResolver = null;

	@Activate
	@Modified
	protected final void activate(ComponentContext ctx) {
		config(ctx.getProperties());
	}

	private void config(Dictionary<?, ?> properties) {
		LOG.debug("activate method called");
		defaultImagePath = PropertiesUtil.toString(properties.get(OSGI_CONFIG_FILE_PATH), DEFAULT_IMG_PATH);

		LOG.debug("event stack list item default image path :: " + defaultImagePath);
	}

	@Override
	public String getEventStackListDefaultImagePath() {
		return defaultImagePath;
	}

	@Override
	public ResourceResolver getAdministrativeResourceResolver() {
		int reTryCount = 0;
		boolean reTry = true;
		while (reTry) {
			try {
				Map<String, Object> param = new HashMap<String, Object>();
				if (resourceResolver != null && resourceResolver.isLive()) {
					resourceResolver.close();
				}
				param.put(ResourceResolverFactory.SUBSERVICE, "csvimporter");
				resourceResolver = resolverFactory.getServiceResourceResolver(param);
				reTry = false;
			} catch (LoginException loginException) {
				LOG.error("Exception while login::Retrying::", loginException);
				reTryCount++;
				if (reTryCount == 3) {
					reTry = false;
				}
			}

		} // end of while
		return resourceResolver;
	}

}
