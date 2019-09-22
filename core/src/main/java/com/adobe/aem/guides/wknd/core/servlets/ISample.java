/**
 * 
 */
package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.resource.ResourceResolver;

/**
 * @author cvalluru
 *
 */
public interface ISample {

	public String getEventStackListDefaultImagePath();
	
	public ResourceResolver getAdministrativeResourceResolver();
}
