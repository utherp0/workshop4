package org.uth.workshopbot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;

@RestController
public class OCPTest1
{
	@RequestMapping("/namespace")
	public String namespace() 
	{
		try
		{
			KubernetesClient client = new DefaultKubernetesClient();
			String namespace = client.getNamespace();
			namespace = ( namespace == null ) ? "None" : namespace;
			client.close();
			
			return namespace;
		}
		catch( Exception exc )
		{
      return "Failed due to " + exc.toString();
		}
	}
}
