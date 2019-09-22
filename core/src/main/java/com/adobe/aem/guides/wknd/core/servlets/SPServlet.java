package com.adobe.aem.guides.wknd.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.eclipse.jetty.io.ssl.ALPNProcessor.Client;
import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@Component(service = Servlet.class,

		property = {

				Constants.SERVICE_DESCRIPTION + "=Salesforce form Servlet",

				"sling.servlet.methods=" + HttpConstants.METHOD_POST,

				"sling.servlet.paths=" + "/bin/thirdparty",

		})
public class SPServlet extends SlingAllMethodsServlet {

	/** The Constant serialVersionUID. */

	private static final long serialVersionUID = 1L;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SPServlet.class);

	@Override

	public void doGet(final SlingHttpServletRequest request,

			final SlingHttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet post = new HttpGet(
				"https://sp10053777.guided.sin2.atomz.com/?authStatus=9;i=1;m_count_content=10;q1=SIM%20Headquarter;sp_cs=UTF-8;x1=location");
//Rest API integration
		// add request header
		HttpResponse response = client.execute(post);

		LOGGER.info("Response Code : " 
	                + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		LOGGER.info("Result is :"+result);
		Gson gson = new Gson();
		
		//Example example = gson.toJson(Example.class);
		//Example emp= gson.fromJson(result.toString(), Example.class);
		
		LOGGER.info("after example");
		//out.println("lower count "+emp.getResultcount().getTotal());
		LOGGER.info("The data is posted to third party");

	}

}
