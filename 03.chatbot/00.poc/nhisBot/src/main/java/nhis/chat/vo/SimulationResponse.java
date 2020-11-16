package nhis.chat.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SimulationResponse {
	private List<Float> intentScores = new ArrayList<Float>();
	private List<SimQuery> simQueryList = new ArrayList<SimQuery>();
	private List<String> intentList = new ArrayList<String>();
	private String answer = "";
	private List<Entry> entries = new ArrayList<Entry>();
	private String topIntentName = "";
	private List<ParameterValue> parameterValueList = new ArrayList<ParameterValue>();
	private List<String> featureList = new ArrayList<String>();
	private String morphAnalysis = "";
	private Boolean isEndOfDialogue = false;
	private Boolean isStartOfDialogue = false;
	private String category="";
	private String categoryName = "";
}
