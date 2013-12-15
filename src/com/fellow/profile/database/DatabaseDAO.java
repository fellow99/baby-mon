package com.fellow.profile.database;

import java.util.*;

public interface DatabaseDAO {
	
	String versionExists();
	
	int getVersion();
	
	void ddl(Map<String, Object> param);
	
	void updateVersion(int version);
}
