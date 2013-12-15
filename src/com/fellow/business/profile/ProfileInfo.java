package com.fellow.business.profile;

import java.util.Date;

public class ProfileInfo {
	private String provider;
	private String userId;
	private int version;
	private int recordCount;
	private Date beginDate;
	private Date recentDate;
	
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getRecentDate() {
		return recentDate;
	}
	public void setRecentDate(Date recentDate) {
		this.recentDate = recentDate;
	}
}
