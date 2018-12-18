package gyqx.txp.message.ext;

import java.util.ArrayList;
import java.util.List;

import gyqx.txp.utils.comparision.file.DirCompareRequest;

public class FileCompareBody 
{
	private List<DirCompareRequest> compareRequests = new ArrayList<>();

	public List<DirCompareRequest> getCompareRequests() {
		return compareRequests;
	}

	public void setCompareRequests(List<DirCompareRequest> compareRequests) {
		this.compareRequests = compareRequests;
	}
	

	
}
