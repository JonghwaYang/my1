package nhis.chat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {
	@Value("#{dmProp['dm.protocol']}")
	public String dmProtocol;
	
	@Value("#{dmProp['dm.ip']}")
	public String dmIp;
	
	@Value("#{dmProp['dm.port']}")
	public String dmPort;

	@Value("#{dmProp['dm.project.id']}")
	public String dmProjectId;
	
	
	@Value("#{dmProp['dm.api.common.sessionRequest']}")
	public String dmApiCommonSessionRequest;
	
	@Value("#{dmProp['dm.api.common.sessionValidation']}")
	public String dmApiCommonSessionValidation;
	
	@Value("#{dmProp['dm.api.common.wiseIChatResponse']}")
	public String dmApiCommonWiseIChatResponse;
	
	@Value("#{dmProp['dm.api.common.sessionTermination']}")
	public String dmApiCommonSessionTermination;
	
	@Value("#{dmProp['dm.api.demo.simulation']}")
	public String dmApiDemoSimulation;
}
