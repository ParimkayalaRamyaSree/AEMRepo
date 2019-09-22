package com.adobe.aem.guides.wknd.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//,

import com.google.gson.JsonObject;

@Component(service=Servlet.class,
property={
        Constants.SERVICE_DESCRIPTION + "=Submit Form Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.paths="+ "/bin/mySubmitFormServlet"
})
public class SubmitFormServlet extends SlingAllMethodsServlet {
    private static final long serialVersionUID = 2598426539166789511L;
	private static final Logger LOG = LoggerFactory.getLogger(SubmitFormServlet.class);  
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServerException, IOException {
     try
     {
    	 LOG.info("---> THIS IS THE POST METHOD OF SubmitFormServlet");
    	 String firstName = request.getParameter("firstName");
         String lastName = request.getParameter("lastName");
         String gender = request.getParameter("gender");
      
         //Encode the submitted form data to JSON
         JsonObject obj= new JsonObject();
         obj.addProperty("firstName",firstName);
         obj.addProperty("lastName",lastName);
         obj.addProperty("gender", gender);
          
         //Return the JSON formatted data
         response.getWriter().write(obj.toString());   	     	
     }
     catch(Exception e)
     {
    	 LOG.error("Exception occured in SubmitServlet"+e.getMessage());
     }
   }
}
