package nhis.chat.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import nhis.chat.vo.ResponseSimulation;
import nhis.chat.vo.SessionRequest;
import nhis.chat.vo.SessionTermination;

@Slf4j
@Service
public class IChatService {

	@Autowired
	private PropertiesService propertiesService;
	
	private ConcurrentHashMap<String, String> userSessionKeyMap = new ConcurrentHashMap<String, String>();

	//ichat simulation api(답변 받아오기)
	public ResponseSimulation getDmApiDemoSimulation(String query) {
		String url = propertiesService.dmProtocol + propertiesService.dmIp + propertiesService.dmPort
				+ propertiesService.dmApiDemoSimulation;
		log.debug("url: " + url);
		log.debug("projectId: " + propertiesService.dmProjectId);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("projectId", propertiesService.dmProjectId);
		paramMap.put("query", query);

		RestTemplate restTemplate = new RestTemplate();
		ResponseSimulation responseSimulation = restTemplate.postForObject(URI.create(url), paramMap,
				ResponseSimulation.class);

		log.debug("responseSimulation : " + responseSimulation);

		return responseSimulation;
	}
	
	//ichat response api(답변 받아오기)
	public ResponseSimulation getDmApiCommonWiseIChatResponse(String userQuery, String uniqueKey){
		String userSessionKey = this.getDmApiCommonSessionValidation(uniqueKey);
		
		String url = propertiesService.dmProtocol + propertiesService.dmIp + propertiesService.dmPort
				+ propertiesService.dmApiCommonWiseIChatResponse;
		
		String projectId = propertiesService.dmProjectId;
		
		log.debug("userQuery: " + userQuery);
		log.debug("url: " + url);
		log.debug("projectId: " + projectId);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectId", projectId);
		paramMap.put("sessionKey", userSessionKey);
		paramMap.put("query", userQuery);
		paramMap.put("isDebug", true);	//simulation response처럼 받기 위해
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseSimulation responseSimulation = restTemplate.postForObject(URI.create(url), paramMap, ResponseSimulation.class);
		
		return responseSimulation;
	}

	//ichat session 생성
	public String getDmApiCommonSessionRequest(String uniqueKey) {
		userSessionKeyMap.remove(uniqueKey);
		String url = propertiesService.dmProtocol + propertiesService.dmIp + propertiesService.dmPort
				+ propertiesService.dmApiCommonSessionRequest;
		log.debug("url: " + url);
		String param = "{}";

		RestTemplate restTemplate = new RestTemplate();
		SessionRequest sessionRequest = restTemplate.postForObject(URI.create(url), param, SessionRequest.class);
		log.debug("sessionRequest : " + sessionRequest);
		return sessionRequest.getSessionKey();
	}
	
	private String getDmApiCommonSessionValidation(String uniqueKey){
		String url = propertiesService.dmProtocol + propertiesService.dmIp + propertiesService.dmPort
				+ propertiesService.dmApiCommonSessionValidation;
		
		Map<String, String> paramMap = new HashMap<String, String>();
		userSessionKeyMap.putIfAbsent(uniqueKey, "");
		paramMap.put("sessionKey", userSessionKeyMap.get(uniqueKey));
		
		log.debug("userSessionKeyMap :: userSessionKey: " + uniqueKey);
		log.debug("userSessionKeyMap :: userSessionKey.get(uniqueKey): " + userSessionKeyMap.get(uniqueKey));

		RestTemplate srestTemplate = new RestTemplate();
		SessionRequest result = srestTemplate.postForObject(URI.create(url), paramMap, SessionRequest.class);

		String userSessionKey = (result.getIsValid()) ? userSessionKeyMap.get(uniqueKey)
				: getDmApiCommonSessionRequest(uniqueKey);
		userSessionKeyMap.put(uniqueKey, userSessionKey);
		log.debug("uniqueKey :: userSessionKey: " + uniqueKey + " :: " + userSessionKey);

		return userSessionKey;
	}

	//ichat session 제거
	public boolean getDmApiCommonSessionTermination(String userSession) {
		String url = propertiesService.dmProtocol + propertiesService.dmIp + propertiesService.dmPort
				+ propertiesService.dmApiCommonSessionTermination;
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionKey", userSession);
		RestTemplate restTemplate = new RestTemplate();
		SessionTermination sessionTermination = restTemplate.postForObject(URI.create(url), paramMap,
				SessionTermination.class);
		log.debug("sessionTermination : " + sessionTermination);
		return sessionTermination.isSuccess();
	}

}
