package com.fellow.profile;

public interface DatabaseBusiness {
	int getProfileVersion();
	
	int getSystemVersion();
	
	void update();
}
