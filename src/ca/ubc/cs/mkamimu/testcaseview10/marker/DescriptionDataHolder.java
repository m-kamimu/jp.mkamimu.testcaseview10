package ca.ubc.cs.mkamimu.testcaseview10.marker;

import java.util.ArrayList;
import java.util.List;

public class DescriptionDataHolder {
	
	private List<DescriptionData> listlastm = new ArrayList<DescriptionData>();
	private DescriptionData nextData = null;
	
	public void clearList() {
		listlastm = new ArrayList<DescriptionData>();
	}
	
	public void clearNextDescriptionData() {
		this.nextData = null;
	}
	
	public DescriptionData getNextItem(String testname) {
		int occurence = -1;
		int maxoccurence = -1;
		if (this.nextData != null) {
			occurence = this.nextData.getOccurence();
		}
		
		List<DescriptionData> listfortestname = new ArrayList<DescriptionData>();
		
		for(int i = 0; i < listlastm.size(); i++) {
			DescriptionData dd = listlastm.get(i);
			if (dd.getTestname().equals(testname)) {
				listfortestname.add(dd);
				if (occurence == -1) {
					occurence = dd.getOccurence();
				} else if (occurence >= dd.getOccurence()) {
					occurence = dd.getOccurence();
				}
				
				if (maxoccurence == -1) {
					maxoccurence = dd.getOccurence();
				} else if (maxoccurence <= dd.getOccurence()) {
					maxoccurence = dd.getOccurence();
				}
			}
		}

		int currentoccurrence = 0;
		int upperlimit = 10000;
		while((nextData == null || currentoccurrence <= maxoccurence) && currentoccurrence < upperlimit) {
			currentoccurrence++;
			for(int j = listfortestname.size()-1 ; j >= 0; j--) {
				DescriptionData dd = listfortestname.get(j);
				if (dd.getOccurence() <= currentoccurrence) {
					if (nextData != dd){
						currentoccurrence = dd.getOccurence();
						nextData = dd;
						return nextData;
					} else {
						continue;
					}
				}
			}
		}
		return null;
	}

	public void add(DescriptionData dd) {
		this.listlastm.add(dd);
	}
	
	public List<String> getTestName(String classname) {
		List<String> testnamelist = new ArrayList<String>();
		
		for(int i = 0; i < listlastm.size(); i++) {
			DescriptionData dd = listlastm.get(i);
			if (classname.equals(dd.getClassname()) 
					&& !testnamelist.contains(dd.getTestname())) {
				testnamelist.add(dd.getTestname());
			}
		}
		return testnamelist;
	}
	
	
}
