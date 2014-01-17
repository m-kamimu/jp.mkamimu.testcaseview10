package ca.ubc.cs.mkamimu.testcaseview10.marker;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.texteditor.MarkerUtilities;

import ca.ubc.cs.mkamimu.testcaseview10.TestInformation;

public class SampleMarker {

	private static final String MARKER_ID = "ca.ubc.cs.mkamimu.testcaseview10.SampleMarker";
	//private static List<String> listlastm = new ArrayList<String>();
	private static DescriptionDataHolder listlastm = new DescriptionDataHolder();
	//private static List<String> listlastm2 = new ArrayList<String>();
	private static DescriptionDataHolder listlastm2 = new DescriptionDataHolder();
	
	/**
	 * @return the lastm
	 */
	public static DescriptionDataHolder getLastm() {
		return listlastm;
	}
	
	/**
	 * @return the lastm2
	 */
	public static DescriptionDataHolder getLastm2() {
		return listlastm2;
	}

	public static int createMarker(IResource resource, TestInformation ti, TestInformation global) {
		listlastm2.clearList();
		//StringBuffer lastm = new StringBuffer();
		//StringBuffer lastm2 = new StringBuffer();
		int marked = 0;
		if (ti == null || global == null) {
			return marked;
		}
		
		try {
			resource.deleteMarkers(SampleMarker.MARKER_ID, false, IResource.DEPTH_ZERO);
			//resource.createMarker("ca.ubc.cs.testcaseview10.mymarker");
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    for(int i = 0; i < ti.getMethodAintsList().size(); i++) {
	    	if (!ti.getMethodAList().get(i).startsWith("assert")) {
	    		continue; // inside assert will be printed by arguments info. 
	    	}
	    	
	    	String classname = ti.getClassName();
	    	String methodname = ti.getMethodAList().get(i);
	    	String methodDname = ti.getMethodADList().get(i);
	    	String methodoname = ti.getMethodOnlyAList().get(i);
	    	String objectName = ti.getExprOnlyAList().get(i);
	    	int linenum = ti.getMethodAintsList().get(i);	    	
	    	int occurence = global.getMethodAOccurence(methodname) - ti.getMethodAOccurenceInTest(methodname, methodDname);
	    	String argumentsname = ti.getMethodAArgList().get(i);
	    	
		    //lastm.append(methodDname + ":");
		    //lastm.append(methodname + "\n");
		    
		    //if (lastm2.indexOf(methodoname) < 0) {
			DescriptionData desdata = new DescriptionData();
			desdata.setClassname(classname);
	    	desdata.setTestname(methodDname);
	    	desdata.setStartcolumn(linenum);
	    	desdata.setOccurence(occurence);
	    	desdata.setAssertInFlag(true);
	    	desdata.setArguments(argumentsname);
	    	desdata.setAssigninfo(ti.getAssigninfo());
	    	
	    	StringBuffer lastm2 = new StringBuffer();
	    	//lastm2.append(linenum + ":");
	    	//lastm2.append(occurence + ":");
		    //lastm2.append(methodDname + ":");
	    	if (ti.getCatchflag().get(i)) {
		    	lastm2.append("when Exception ");
	    	} else if (ti.getTryflag().get(i)) {
		    	lastm2.append("Try to ");
	    	}
	    	lastm2.append("Check ");
	    	if (methodname.startsWith("assert")) {
	    		lastm2.append(methodoname.substring(methodoname.indexOf("assert") + 6) + " for ");
	    	} else {
	    		lastm2.append(methodoname);
	    	}
	    	if (desdata.getArguments().length() > 2) { // for [] description
	    		lastm2.append(" " + desdata.getArguments() + " ");
	    	}
		    if (objectName != null && !objectName.equals("")) {
		    	if (methodoname.startsWith("set")) {
		    		lastm2.append(" to " + objectName);
		    	} else if (methodoname.startsWith("get")) {
		    		lastm2.append(" from " + objectName);
		    	} else if (!objectName.equals("")) {
		    		lastm2.append(" on " + objectName);
		    	}
		    }
		    lastm2.append("\n");
		    desdata.setDescription(lastm2.toString());
		    listlastm2.add(desdata);
		    lastm2.delete(0, lastm2.length());
		    //}
	    	
	    	if (occurence >= 1) {
	    		continue;
	    	}
	    	
		    Map<Object,Object> attributes = new HashMap<Object,Object>();
		    attributes.put(IMarker.TRANSIENT, true);
		    attributes.put(IMarker.PRIORITY, Integer.valueOf(IMarker.PRIORITY_NORMAL));
		    attributes.put(IMarker.SEVERITY, Integer.valueOf(IMarker.SEVERITY_WARNING));
		    //attributes.put(IMarker.LINE_NUMBER, Integer.valueOf(100));

	    	attributes.put(IMarker.CHAR_START, Integer.valueOf(ti.getMethodAintsList().get(i)));
		    attributes.put(IMarker.CHAR_END, Integer.valueOf(ti.getMethodAintsList().get(i) + ti.getMethodAintlList().get(i)));

		    
		    attributes.put(IMarker.MESSAGE, "methodname: " + methodname + " occurence(in others):" + occurence);
		    System.out.println(classname + ":" + methodDname + ": " + methodname + " occurence(in others):" + occurence);
		    try {
				MarkerUtilities.createMarker(resource, attributes, SampleMarker.MARKER_ID);
				marked++;
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
		return marked;
	}
}
