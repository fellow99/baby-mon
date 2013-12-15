package com.fellow.profile;

import java.util.*;

public interface RecordBusiness {

	void insert(String type, Date recordDate, float amount, String info);
	
	void delete(String type, String id);
	
	List<Map<String, Object>> list(Date dateFrom, Date dateTo);

	List<Map<String, Object>> listByType(String type, Date dateFrom, Date dateTo);
	
	Map<String, Object> last(String type);
	
	Float sum(String type, Date dateFrom, Date dateTo);
}
