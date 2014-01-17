package ca.ubc.cs.mkamimu.testcaseview10.marker;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs.mkamimu.testcaseview10.AssignInformation;

public class DescriptionData {
	
	private int occurence = 0;
	private int startcolumn = 0;
	
	private String classname;
	private String testname;
	private String description;
	private String arguments;
	private List<AssignInformation> assigninfo = new ArrayList<AssignInformation>();
	
	private boolean assertInFlag = false;	
	
	public int getOccurence() {
		return occurence;
	}
	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}
	public int getStartcolumn() {
		return startcolumn;
	}
	public void setStartcolumn(int startcolumn) {
		this.startcolumn = startcolumn;
	}
	public String getTestname() {
		return testname;
	}
	public void setTestname(String testname) {
		this.testname = testname;
	}
	public String getDescription() {
		if (description.endsWith("\n")) {
			return description.substring(0, description.length() - 1);
		} else {
			return description;
		}
	}
	
	public String getDescriptionWithReplace() {
		String tmp = description;
		for(int i = assigninfo.size()-1; i >= 0; i--) {
			List<String> left = assigninfo.get(i).getLeftList();
			List<String> right = assigninfo.get(i).getRightList(); // should be same length for right and left
			for(int j = 0; j < left.size(); j++) {
				if (left.get(j).length() < 3
						|| (left.get(j).length() < 6 && right.get(j).length() < 15)
						|| left.get(j).length() > 6) {
					tmp = tmp.replace(left.get(j), right.get(j));
				}
			}
		}
		if (tmp.endsWith("\n")) {
			return tmp.substring(0, tmp.length() - 1);
		} else {
			return tmp;
		}
	}
	
	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getArguments() {
		return arguments;
	}
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public boolean isAssertInFlag() {
		return assertInFlag;
	}
	public void setAssertInFlag(boolean assertInFlag) {
		this.assertInFlag = assertInFlag;
	}
	public void setAssigninfo(List<AssignInformation> assigninfo) {
		if (testname == null || testname.equals("")) {
			return;
		}

		for(int i = 0; i < assigninfo.size(); i++) {
			if (assigninfo.get(i).getTestinfo().equals(testname)) {
				this.assigninfo.add(assigninfo.get(i));
			}
		}
		return;
	}
	
	

}
