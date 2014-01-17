package ca.ubc.cs.mkamimu.testcaseview10;

import java.util.ArrayList;
import java.util.List;

public class TestInformation2 {
	private String className = new String(); // test class name
	
	private List<String> methodDList = new ArrayList<String>(); // test case name
	
	private List<String> methodIDList = new ArrayList<String>(); // ordinary list (test name)
	private List<String> methodADList = new ArrayList<String>(); // assert list (test name)
	
	private List<List<String>> methodIArgList = new ArrayList<List<String>>(); // ordinary argument list (invocation)
	private List<List<String>> methodAArgList = new ArrayList<List<String>>(); // assert argument list (invocation)


}
