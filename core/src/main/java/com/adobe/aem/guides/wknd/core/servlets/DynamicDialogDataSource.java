package com.adobe.aem.guides.wknd.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.jcr.Node;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.EmptyDataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;

@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=Component Dropdown options fetching through Servlet",
		"sling.servlet.resourceTypes=/apps/dailog/dropdown/json", "sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class DynamicDialogDataSource extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(DynamicDialogDataSource.class);

	private static final String OPTIONS_PROPERTY = "options";

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
			ResourceResolver resolver = request.getResourceResolver();

			request.setAttribute(DataSource.class.getName(), EmptyDataSource.instance());

			Resource datasource = request.getResource().getChild("datasource");
			ValueMap dsProperties = ResourceUtil.getValueMap(datasource);
			String genericListPath = dsProperties.get(OPTIONS_PROPERTY, String.class);

			Resource genericListresource = request.getResource().getResourceResolver()
					.getResource(genericListPath + "/jcr:content");
			if (genericListresource != null) {
				Node cNode = genericListresource.adaptTo(Node.class);
				if (cNode != null) {
				InputStream in = cNode.getProperty("jcr:data").getBinary().getStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder out = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						out.append(line);
					}
					reader.close();

					ValueMap vm = null;
					List<Resource> optionResourceList = new ArrayList<>();
					Gson gson = new Gson();
					JsonElement element = gson.fromJson(out.toString(), JsonElement.class);
					JsonArray jsonArray = element.getAsJsonArray();
					Iterator<JsonElement> it = jsonArray.iterator();
					while (it.hasNext()) {
						JsonElement jsonElement = it.next();
						vm = new ValueMapDecorator(new HashMap<String, Object>());

						vm.put("value", jsonElement.getAsJsonObject().get("value").getAsString());
						vm.put("text", jsonElement.getAsJsonObject().get("text").getAsString());
						optionResourceList
								.add(new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm));

					}
					DataSource ds = new SimpleDataSource(optionResourceList.iterator());
					request.setAttribute(DataSource.class.getName(), ds);

				} else {
					LOG.info("JSON file is not found ");
				}
			}
		}

		catch (Exception e) {
			LOG.error("DynamicDialogDataSource:: Error in Get Drop Down Values", e);
		}
	}

}
