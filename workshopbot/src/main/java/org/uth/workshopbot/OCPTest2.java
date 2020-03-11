package org.uth.workshopbot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.api.model.ConfigBuilder;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.openshift.client.OpenShiftClient;
import java.util.*;

@RestController
public class OCPTest2
{
	private static DefaultKubernetesClient _client = null;
	
	private boolean login()
	{
		String clusterURL = System.getenv("CLUSTER");
		String openshiftUser = System.getenv("OCUSER");
		String openshiftPassword = System.getenv("OSPASS");

		if( clusterURL == null || openshiftUser == null || openshiftPassword == null ) return false;

		return false;
	}

	@RequestMapping("/podnames")
	public String getNames()
	{
		StringBuilder output = new StringBuilder();
		long start = System.currentTimeMillis();
	
		System.out.println( "/podnames request received.");
		try
		{
			if(_client == null ) _client = new DefaultKubernetesClient();
			String namespace = _client.getNamespace();
	
			output.append( "Namespace: " + namespace + " in " + ( System.currentTimeMillis() - start) + "ms elapsed<br/>");
	
			PodList pods = _client.pods().list();
	
			for( Pod pod : pods.getItems() )
			{
				// Only process running Pods
				String phase = pod.getStatus().getPhase();

				if( "Running".equals(phase))
				{
					output.append( "<b><i>Found Pod " + pod.getMetadata().getName() + "</b></i><br/>");
					output.append( "&nbsp;&nbsp;<b>[STATUS]</b><br/>");
				  //output.append( (pod.getMetadata().getGenerateName() == null ) ? "" : "  Generate Name: " + pod.getMetadata().getGenerateName() + "\n");
				  output.append( "&nbsp;&nbsp;&nbsp;&nbsp;Phase: " + pod.getStatus().getPhase() + "<br/>");
					output.append( "&nbsp;&nbsp;&nbsp;&nbsp;Pod SDN IP: " + pod.getStatus().getPodIP() + "<br/>");
					output.append( "&nbsp;&nbsp;&nbsp;&nbsp;Pod Host IP: " + pod.getStatus().getHostIP() + "<br/>");
					output.append( "&nbsp;&nbsp;&nbsp;&nbsp;Start Time: " + pod.getStatus().getStartTime() + "<br/>");

          output.append( "&nbsp;&nbsp;<b>[METADATA]</b><br/>");
					output.append( "&nbsp;&nbsp;&nbsp;&nbsp;Self Link: " + pod.getMetadata().getSelfLink() + "<br/>");
					output.append( "&nbsp;&nbsp;&nbsp;&nbsp;Pod Name/Namespace: " + pod.getMetadata().getName() + "/" + pod.getMetadata().getNamespace() + "<br/>");
					output.append( "&nbsp;&nbsp;<b>[SPEC]</b><br/>");
					output.append( "&nbsp;&nbsp;&nbsp;&nbsp;" + pod.getSpec().getNodeName() + "<br/>");


				  Map<String,String> labels = pod.getMetadata().getLabels();

				  for( String label : labels.keySet()) 
				  {   
					  //System.out.println( label + ":" + labels.get( label )) ;
					}

					output.append( "<br/><br/>");
				}
			}
	
			return output.toString();
		}
		catch( Exception exc )
		{
			return "Failed due to " + exc.toString();
		}	
	}

	@RequestMapping("/podtest")
	public String namespace() 
	{
		StringBuilder output = new StringBuilder();
		long start = System.currentTimeMillis();

		System.out.println( "/podtest request received.");
		try
		{
			if(_client == null ) _client = new DefaultKubernetesClient();
			String namespace = _client.getNamespace();

			System.out.println( "Namespace: " + ( namespace == null ? "No namespaces" : namespace ));
			System.out.println( "" );

      output.append( "Namespace: " + namespace + " in " + ( System.currentTimeMillis() - start) + "ms elapsed\n");

			PodList pods = _client.pods().list();

			for( Pod pod : pods.getItems() )
			{
				output.append( "Found Pod " + pod.getMetadata().getName() + "\n");

				System.out.println( "  " + pod.getStatus().toString());
				System.out.println( "" );
			}

			return output.toString();
		}
		catch( Exception exc )
		{
      return "Failed due to " + exc.toString();
		}
	}
}
