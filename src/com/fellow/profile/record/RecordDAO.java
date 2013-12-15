package com.fellow.profile.record;

import java.util.*;

public interface RecordDAO {
	
	List<Map<String, Object>> list(Map<String, Object> query);
	
	Map<String, Object> last(String type);
	
	Float sum(Map<String, Object> query);
	
	void insert(Map<String, Object> data);
	
	void delete(Map<String, Object> data);
	
}
