package com.adobe.aem.guides.wknd.core.servlets;

import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;

@Component
@Service
@Properties({
		@Property(name = Constants.SERVICE_DESCRIPTION, value = "To Check the profile nodes has replication status property or not"),
		@Property(name = Constants.SERVICE_VENDOR, value = "Comp"),
		@Property(name = "process.label", value = "Check Profile Replication Status") })


public class CheckProfileReplicationStatus implements WorkflowProcess {

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckProfileReplicationStatus.class);

	private ResourceResolver resourceResolver;
	
	private Session adminSession;
	/**
	 * Executes a new Java process with the given WorkItem and WorkflowSession.
	 * 
	 * @param workItem
	 *            The WorkItem that defines the newly started JavaProcess.
	 * @param workflowSession
	 *            The WorkFlowSession that is used for starting the JavaProcess.
	 * @param metaDataMap
	 *            Process specific arguments can be passed here.
	 * @throws WorkflowException
	 *             Thrown in case something goes wrong during execution.
	 */
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		try {
			WorkflowData workflowData = workItem.getWorkflowData();
			String payloadPath = workflowData.getPayload().toString();
			Resource profileResource = resourceResolver.getResource(payloadPath);
			ValueMap profileMap = profileResource.adaptTo(ValueMap.class);
			String replicationStatus = profileMap.get("replicationStatus", String.class);
			if(StringUtils.isNotBlank(replicationStatus)){
				if(StringUtils.equalsIgnoreCase(replicationStatus, "true")){
					Resource updateResource = resourceResolver.getResource(payloadPath);
					ModifiableValueMap properties = updateResource.adaptTo(ModifiableValueMap.class);
					properties.put("replicationStatus", "false");
					resourceResolver.commit();
				}
			}

		} catch (Exception exception) {
			LOGGER.error("Failed to execute in CheckProfileReplicationStatus Process Workflow", exception);
		} finally {
			if (resourceResolver != null && resourceResolver.isLive()) {
				resourceResolver.close();
			}
		}
	}

}

