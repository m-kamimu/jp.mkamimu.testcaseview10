package ca.ubc.cs.mkamimu.testcaseview10.marker;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.texteditor.MarkerUtilities;

import ca.ubc.cs.mkamimu.testcaseview10.TestInformation;

public class SampleMarker2 {

	private static final String MARKER_ID = "ca.ubc.cs.mkamimu.testcaseview10.SampleMarker2";
	//private static StringBuffer lastm = new StringBuffer();
	//private static StringBuffer lastm2 = new StringBuffer();
	private static DescriptionDataHolder listlastm = new DescriptionDataHolder();
	private static DescriptionDataHolder listlastm2 = new DescriptionDataHolder();

	/**
	 * @return the lastm
	 */
	public static DescriptionDataHolder getLastm() {
		return listlastm;
	}
	
	/**
	 * @return the lastm
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
			resource.deleteMarkers(SampleMarker2.MARKER_ID, false, IResource.DEPTH_ZERO);
			//resource.createMarker("ca.ubc.cs.testcaseview10.mymarker");
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    for(int i = 0; i < ti.getMethodIintsList().size(); i++) {
	    	String classname = ti.getClassName();
	    	String methodname = ti.getMethodIList().get(i);
	    	String methodDname = ti.getMethodIDList().get(i);
	    	
	    	String methodoname = ti.getMethodOnlyIList().get(i);
	    	String objectName = ti.getExprOnlyIList().get(i);
	    	int linenum = ti.getMethodIintsList().get(i);
	    	int occurence = global.getMethodIOccurence(methodname) - ti.getMethodIOccurenceInTest(methodname, methodDname);
	    	String argumentsname = ti.getMethodIArgList().get(i);
	    	
	    	DescriptionData desdata = new DescriptionData();
			desdata.setClassname(classname);
	    	desdata.setTestname(methodDname);
	    	desdata.setStartcolumn(linenum);
	    	desdata.setOccurence(occurence);
	    	desdata.setArguments(argumentsname);
	    	desdata.setAssigninfo(ti.getAssigninfo());
	    	
	    	//if (lastm2.indexOf(methodoname) < 0) {
	    	StringBuffer lastm2 = new StringBuffer();
	    	if (ti.getCatchflag().get(i)) {
		    	lastm2.append("when Exception ");
	    	} else if (ti.getTryflag().get(i)) {
		    	lastm2.append("Try to ");
	    	}

		    lastm2.append(methodoname);
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
	    	
	    	if (occurence >= 1) {
	    		continue;
	    	}

		    Map<Object,Object> attributes = new HashMap<Object,Object>();
		    attributes.put(IMarker.TRANSIENT, true);
		    attributes.put(IMarker.PRIORITY, Integer.valueOf(IMarker.PRIORITY_NORMAL));
		    attributes.put(IMarker.SEVERITY, Integer.valueOf(IMarker.SEVERITY_WARNING));
		    //attributes.put(IMarker.LINE_NUMBER, Integer.valueOf(100));

	    	attributes.put(IMarker.CHAR_START, Integer.valueOf(ti.getMethodIintsList().get(i)));
		    attributes.put(IMarker.CHAR_END, Integer.valueOf(ti.getMethodIintsList().get(i) + ti.getMethodIintlList().get(i)));
		    //lastm.append(methodDname + ":");
		    //lastm.append(methodname + "\n");
		    
		    
		    //attributes.put(IMarker.CHAR_START, Integer.valueOf(1000));
		    //attributes.put(IMarker.CHAR_END, Integer.valueOf(1005));
		    attributes.put(IMarker.MESSAGE, "methodname: " + methodname + " occurence(in others):" + occurence);
		    System.out.println(classname + ":" + methodDname + ": " + methodname + " occurence(in others):" + occurence);
		    try {
				MarkerUtilities.createMarker(resource, attributes, SampleMarker2.MARKER_ID);
				marked++;
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
	    return marked;
	}	
}
