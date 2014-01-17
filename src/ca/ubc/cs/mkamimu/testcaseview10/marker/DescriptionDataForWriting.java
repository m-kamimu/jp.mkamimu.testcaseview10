package ca.ubc.cs.mkamimu.testcaseview10.marker;

import java.util.ArrayList;
import java.util.List;

public class DescriptionDataForWriting {
	List<DescriptionData> descriptionlist = new ArrayList<DescriptionData>();
	
	public void add(DescriptionData dd) {
		if (dd != null && !this.descriptionlist.contains(dd)) {
			this.descriptionlist.add(dd);
		}
	}
	
	
	public List<DescriptionData> getDescriptionInOrder() {
		List<DescriptionData> tmplist = new ArrayList<DescriptionData>();
		int startcount = -1;
		DescriptionData currentdd = null;
		while(descriptionlist.size() != tmplist.size()) {
			for(int i = 0; i < descriptionlist.size(); i++) {
				DescriptionData dd = descriptionlist.get(i);
				if (tmplist.contains(dd)) {
					continue;
				}
				if (startcount == -1 || currentdd == null) {
					startcount = dd.getStartcolumn();
					currentdd = dd;
				} else if (startcount >= dd.getStartcolumn()) {					
					startcount = dd.getStartcolumn();
					currentdd = dd;
				}
			}
			if (!tmplist.contains(currentdd) && currentdd != null) {
				tmplist.add(currentdd);
				startcount = currentdd.getStartcolumn();
				//currentdd = null;
			} else {
				startcount++;
			}
		}
		return tmplist;
	}
	
	public int size() {
		return descriptionlist.size();
	}
	
}
