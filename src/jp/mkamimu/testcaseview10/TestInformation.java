package jp.mkamimu.testcaseview10;

import java.util.ArrayList;
import java.util.List;

public class TestInformation {
	
	private List<String> methodDList = new ArrayList<String>(); // test case name

	private List<AssignInformation> assigninfo = new ArrayList<AssignInformation>();
	
	/**
	 * @return
	 */
	public List<AssignInformation> getAssigninfo() {
		return assigninfo;
	}
	
	/**
	 * @param assigninfo
	 */
	public void setAssigninfo(List<AssignInformation> assigninfo) {
		this.assigninfo = assigninfo;
	}
	
	/**
	 * @param assigninfo
	 */
	public void addAssigninfo(AssignInformation assigninfo) {
		this.assigninfo.add(assigninfo);
	}
	
	/**
	 * @return the methodDList
	 */
	public List<String> getMethodDList() {
		return methodDList;
	}
	/**
	 * @param methodDList the methodDList to set
	 */
	public void setMethodDList(List<String> methodDList) {
		this.methodDList = methodDList;
	}
}
