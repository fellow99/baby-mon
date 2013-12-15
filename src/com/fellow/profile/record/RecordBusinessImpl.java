package com.fellow.profile.record;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fellow.profile.RecordBusiness;
import com.fellow.util.LowerCaseKeyMap;

@Service
public class RecordBusinessImpl implements RecordBusiness{
	
	private RecordDAO recordDAO;

	public RecordDAO getRecordDAO() {
		return recordDAO;
	}

	public void setRecordDAO(RecordDAO recordDAO) {
		this.recordDAO = recordDAO;
	}

	@Override
	public void insert(String type, Date recordDate, float amount, String info) {
	    Map<String, Object> data = new LowerCaseKeyMap<Object>();
	    data.put("RECORD_TYPE", type);
	    data.put("RECORD_DATE", recordDate);
	    data.put("AMOUNT", amount);
	    data.put("INFO", info);
	    
	    recordDAO.insert(data);
	}

	@Override
	public void delete(String type, String id) {
	    Map<String, Object> data = new LowerCaseKeyMap<Object>();
	    data.put("RECORD_TYPE", type);
	    data.put("ID", id);
	    
	    recordDAO.delete(data);
	}

	@Override
	public List<Map<String, Object>> list(Date dateFrom, Date dateTo) {
	    Map<String, Object> query = new LowerCaseKeyMap<Object>();
	    query.put("RECORD_DATE_FROM", dateFrom);
	    query.put("RECORD_DATE_TO", dateTo);
	    
	    return recordDAO.list(query);
	}

	@Override
	public List<Map<String, Object>> listByType(String type, Date dateFrom, Date dateTo) {
	    Map<String, Object> query = new LowerCaseKeyMap<Object>();
	    query.put("RECORD_TYPE", type);
	    query.put("RECORD_DATE_FROM", dateFrom);
	    query.put("RECORD_DATE_TO", dateTo);
	    
	    return recordDAO.list(query);
	}

	@Override
	public Float sum(String type, Date dateFrom, Date dateTo) {
	    Map<String, Object> query = new LowerCaseKeyMap<Object>();
	    query.put("RECORD_TYPE", type);
	    query.put("RECORD_DATE_FROM", dateFrom);
	    query.put("RECORD_DATE_TO", dateTo);
	    
	    return recordDAO.sum(query);
	}

	@Override
	public Map<String, Object> last(String type) {
		return recordDAO.last(type);
	}
	
}
