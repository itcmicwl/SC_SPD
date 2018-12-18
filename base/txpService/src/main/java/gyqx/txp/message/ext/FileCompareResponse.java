package gyqx.txp.message.ext;

import java.util.ArrayList;
import java.util.List;


public class FileCompareResponse 
{
	private List<FileCompareRespItem> results = new ArrayList<>(1);
	
	private String script;

	public List<FileCompareRespItem> getResults() {
		return results;
	}

	public void setResults(List<FileCompareRespItem> results) {
		this.results = results;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public String toString() {
		return "FileCompareResponse [results=" + results.size() + ", script=" + script + "]";
	}

	
}
