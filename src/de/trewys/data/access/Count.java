package de.trewys.data.access;

import de.trewys.data.DataObject;

public class Count implements DataObject {
	
	private int count;

	
	public Count(int count) {
		super();
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public Long getId() {
		return null;
	}
	
	

}
