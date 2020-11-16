package nhis.chat.vo;

import lombok.Data;

@Data
public class ResponseSimulation {
	private String errorMessage = "";
	private SimulationResponse response = new SimulationResponse();
	private String status = "";
	private int errorCode = 0;
	private String api = "";
	private String sessionKey = "";
	private Boolean isValid = false; 
	
}
