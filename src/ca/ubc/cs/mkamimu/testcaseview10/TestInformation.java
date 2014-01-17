package ca.ubc.cs.mkamimu.testcaseview10;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kamimura
 *
 */
public class TestInformation {
	
	private String className = new String(); // test class name
	
	private List<String> methodDList = new ArrayList<String>(); // test case name
	
	private List<String> methodIDList = new ArrayList<String>(); // ordinary list (test name)
	private List<String> methodADList = new ArrayList<String>(); // assert list (test name)
	
	private List<String> methodIList = new ArrayList<String>(); // ordinary list (invocation)
	private List<String> methodAList = new ArrayList<String>(); // assert list (invocation)

	private List<String> methodOnlyIList = new ArrayList<String>(); // ordinary list (invocation)
	private List<String> methodOnlyAList = new ArrayList<String>(); // assert list (invocation)

	private List<String> exprOnlyIList = new ArrayList<String>(); // ordinary list (invocation)
	private List<String> exprOnlyAList = new ArrayList<String>(); // assert list (invocation)
	
	private List<Integer> methodIintsList = new ArrayList<Integer>();  //start position column number
	private List<Integer> methodIintlList = new ArrayList<Integer>();  //length of the description

	private List<Integer> methodAintsList = new ArrayList<Integer>();  // start position column number
	private List<Integer> methodAintlList = new ArrayList<Integer>();  // length of the description
	
	private List<String> methodIArgList = new ArrayList<String>(); // ordinary argument list (invocation)
	private List<String> methodAArgList = new ArrayList<String>(); // assert argument list (invocation)

	//private List<String> sourceFiles = new ArrayList<String>();
	private boolean lock = false;
	
	private List<Boolean> tryflag = new ArrayList<Boolean>();
	private List<Boolean> catchflag = new ArrayList<Boolean>();

	private List<AssignInformation> assigninfo = new ArrayList<AssignInformation>();
	
	/**
	 * @return the sourceFiles
	 */
	/*public List<String> getSourceFiles() {
		return sourceFiles;
	}
	/**
	 * @param sourceFiles the sourceFiles to set
	 */
	/*
	public void setSourceFile(String sourceFile) {
		this.sourceFiles.add(sourceFile);
	}
	*/
	
	/**
	 * @return the lock
	 */
	public boolean isLock() {
		return lock;
	}
	/**
	 * @param lock the lock to set
	 */
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	/**
	 * @return the methodIintlList
	 */
	public List<Integer> getMethodIintlList() {
		return methodIintlList;
	}
	/**
	 * @param methodIintlList the methodIintlList to set
	 */
	public void setMethodIintlList(List<Integer> methodIintlList) {
		this.methodIintlList = methodIintlList;
	}
	/**
	 * @return the methodAintlList
	 */
	public List<Integer> getMethodAintlList() {
		return methodAintlList;
	}
	/**
	 * @param methodAintlList the methodAintlList to set
	 */
	public void setMethodAintlList(List<Integer> methodAintlList) {
		this.methodAintlList = methodAintlList;
	}
	/**
	 * @return the methodIintList
	 */
	public List<Integer> getMethodIintsList() {
		return methodIintsList;
	}
	/**
	 * @param methodIintList the methodIintList to set
	 */
	public void setMethodIintsList(List<Integer> methodIintsList) {
		this.methodIintsList = methodIintsList;
	}
	/**
	 * @return the methodAintList
	 */
	public List<Integer> getMethodAintsList() {
		return methodAintsList;
	}
	/**
	 * @param methodAintList the methodAintList to set
	 */
	public void setMethodAintsList(List<Integer> methodAintsList) {
		this.methodAintsList = methodAintsList;
	}
	
	/**
	 * @param methodname
	 * @return
	 */
	public int getMethodAOccurence(String methodname) {
		int occurence = 0;
		for(String name: methodAList) {
			if (name.equals(methodname)) {
				occurence++;
			}
		}
		return occurence;
	}
	
	/**
	 * @param methodname
	 * @param methodDname
	 * @return
	 */
	public int getMethodAOccurenceInTest(String methodname, String methodDname) {
		int occurence = 0;
		for(int i = 0; i < methodADList.size(); i++) {
			String dname = methodADList.get(i);
			String name = methodAList.get(i);
			if (name.equals(methodname) && dname.equals(methodDname)) {
				occurence++;
			}
		}
		return occurence;
	}
	
	/**
	 * @param methodname
	 * @return
	 */
	public int getMethodIOccurence(String methodname) {
		int occurence = 0;
		for(String name: methodIList) {
			if (name.equals(methodname)) {
				occurence++;
			}
		}
		return occurence;
	}

	/**
	 * @param methodname
	 * @param methodDname
	 * @return
	 */
	public int getMethodIOccurenceInTest(String methodname, String methodDname) {
		int occurence = 0;
		for(int i = 0; i < methodIDList.size(); i++) {
			String dname = methodIDList.get(i);
			String name = methodIList.get(i);
			if (name.equals(methodname) && dname.equals(methodDname)) {
				occurence++;
			}
		}
		return occurence;
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
	/**
	 * @return the methodIList
	 */
	public List<String> getMethodIList() {
		return methodIList;
	}
	/**
	 * @param methodIList the methodIList to set
	 */
	public void setMethodIList(List<String> methodIList) {
		this.methodIList = methodIList;
	}
	/**
	 * @return the methodAList
	 */
	public List<String> getMethodAList() {
		return methodAList;
	}
	/**
	 * @param methodAList the methodAList to set
	 */
	public void setMethodAList(List<String> methodAList) {
		this.methodAList = methodAList;
	}
	/**
	 * @return the methodIDList
	 */
	public List<String> getMethodIDList() {
		return methodIDList;
	}
	/**
	 * @param methodIDList the methodIDList to set
	 */
	public void setMethodIDList(List<String> methodIDList) {
		this.methodIDList = methodIDList;
	}
	/**
	 * @return the methodADList
	 */
	public List<String> getMethodADList() {
		return methodADList;
	}
	/**
	 * @param methodADList the methodADList to set
	 */
	public void setMethodADList(List<String> methodADList) {
		this.methodADList = methodADList;
	}
	
	/**
	 * @return
	 */
	public List<String> getMethodOnlyIList() {
		return methodOnlyIList;
	}
	/**
	 * @param methodOnlyIList
	 */
	public void setMethodOnlyIList(List<String> methodOnlyIList) {
		this.methodOnlyIList = methodOnlyIList;
	}
	/**
	 * @return
	 */
	public List<String> getMethodOnlyAList() {
		return methodOnlyAList;
	}
	/**
	 * @param methodOnlyAList
	 */
	public void setMethodOnlyAList(List<String> methodOnlyAList) {
		this.methodOnlyAList = methodOnlyAList;
	}
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
	 * @return
	 */
	public List<String> getExprOnlyIList() {
		return exprOnlyIList;
	}
	/**
	 * @param exprOnlyIList
	 */
	public void setExprOnlyIList(List<String> exprOnlyIList) {
		this.exprOnlyIList = exprOnlyIList;
	}
	/**
	 * @return
	 */
	public List<String> getExprOnlyAList() {
		return exprOnlyAList;
	}
	/**
	 * @param exprOnlyAList
	 */
	public void setExprOnlyAList(List<String> exprOnlyAList) {
		this.exprOnlyAList = exprOnlyAList;
	}
	/**
	 * @return
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param classname
	 */
	public void setClassName(String classname) {
		this.className = classname;
	}
	/**
	 * @return
	 */
	public List<String> getMethodIArgList() {
		return methodIArgList;
	}
	/**
	 * @param methodIArgList
	 */
	public void setMethodIArgList(List<String> methodIArgList) {
		this.methodIArgList = methodIArgList;
	}
	/**
	 * @return
	 */
	public List<String> getMethodAArgList() {
		return methodAArgList;
	}
	/**
	 * @param methodAArgList
	 */
	public void setMethodAArgList(List<String> methodAArgList) {
		this.methodAArgList = methodAArgList;
	}
	public List<Boolean> getTryflag() {
		return tryflag;
	}
	public List<Boolean> getCatchflag() {
		return catchflag;
	}
	
}
