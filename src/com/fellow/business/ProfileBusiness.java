package com.fellow.business;

import javax.sql.DataSource;

import com.fellow.business.profile.ProfileInfo;
import com.fellow.every.auth.AccessToken;
import com.fellow.profile.DatabaseBusiness;
import com.fellow.profile.RecordBusiness;

public interface ProfileBusiness {

	ProfileInfo getProfile(String provider, String userId);
	
	boolean existsProfile(String provider, String userId);
	
	void updateProfile(String provider, String userId);
	
	void createProfile(String provider, String userId);
	
	void deleteProfile(String provider, String userId);
	
	void resetProfile(String provider, String userId);
	
	AccessToken getAccessToken(String provider, String userId);
	
	void saveAccessToken(String provider, String userId, String raw);
	
	DataSource getDataSource(String provider, String userId);
	
	DatabaseBusiness getDatabaseBusiness(String provider, String userId);
	
	RecordBusiness getRecordBusiness(String provider, String userId);
}
