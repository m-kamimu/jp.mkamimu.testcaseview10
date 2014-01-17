package ca.ubc.cs.mkamimu.testcaseview10;

import java.util.ArrayList;
import java.util.List;

public class AssignInformation {
	
	private String testinfo = new String();

	private List<String> leftList = new ArrayList<String>();  //assigned variable (left)
	private List<String> rightList = new ArrayList<String>();  //assigning value (right)
	
	private List<Integer> assignsList = new ArrayList<Integer>();  //start position column number
	private List<Integer> assignlList = new ArrayList<Integer>();  //length of the description
	
	public String getTestinfo() {
		return testinfo;
	}
	public void setTestinfo(String testinfo) {
		this.testinfo = testinfo;
	}
	public void setAssignInfo(String left, String right, int start, int length) {
		this.leftList.add(left);
		this.rightList.add(right);
		this.assignsList.add(start);
		this.assignlList.add(length);
	}
	public List<String> getLeftList() {
		return leftList;
	}
	public List<String> getRightList() {
		return rightList;
	}
}
