package com.adobe.aem.guides.wknd.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//,
//selectors = {"testservlet"}

/*@SlingServlet(methods = {"GET"}, 
metatype = true,
resourceTypes = {"wknd/components/structure/page"})
*/

/*@Component(service=Servlet.class,
property={
        Constants.SERVICE_DESCRIPTION + "=Submit Resource Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.resourceTypes="+ "wknd/components/structure/page"
})*/
@Component(service=Servlet.class,
property={
        Constants.SERVICE_DESCRIPTION + "=Submit Resource Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.resourceTypes="+ "wknd/components/structure/page1"
})
public class SubmitResourceServlet extends SlingSafeMethodsServlet {
    private static final long serialVersionUID = 2598426539166789515L;
	private static final Logger LOG = LoggerFactory.getLogger(SubmitResourceServlet.class);  
	@org.apache.felix.scr.annotations.Reference
	private ResourceResolverFactory resourceResolverFactory;    
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
     try
     {
    	 LOG.info("---> THIS IS THE GET METHOD OF wknd/components/structure/page");
    	 
    	 /*LOG.info("resourceResolverFactory"+resourceResolverFactory.toString());
    		
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
    	 */
    	
         PrintWriter out = response.getWriter();
         out.println("<html><body>");
         out.println("<h1>This value was returned by an AEM Sling Servlet bound by using a Sling ResourceTypes property</h1>");
         out.println("</html></body>");
          
     }
     catch(Exception e)
     {
    	 LOG.error("Exception occured in SubmitServlet"+e.getMessage());
     }
   }
}
