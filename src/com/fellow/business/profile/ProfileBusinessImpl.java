package com.fellow.business.profile;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.fellow.business.EveryCloudBusiness;
import com.fellow.business.ProfileBusiness;
import com.fellow.component.vfs.VFSComponent;
import com.fellow.every.auth.AccessToken;
import com.fellow.profile.DatabaseBusiness;
import com.fellow.profile.RecordBusiness;

@Service
public class ProfileBusinessImpl implements ProfileBusiness, ApplicationContextAware{

	private String profilesPath;
	private ApplicationContext parentContext;
	private ServletContext servletContext;
	private VFSComponent vfsComponent;
	private EveryCloudBusiness everyCloudBusiness;

	private Map<String, ApplicationContext> profileContexts;
	
	public ProfileBusinessImpl(){
		profileContexts = new ConcurrentHashMap<String, ApplicationContext>();
	}


	public String getProfilesPath() {
		return profilesPath;
	}

	public void setProfilesPath(String profilesPath) {
		this.profilesPath = profilesPath;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext parentContext)
			throws BeansException {
		this.parentContext = parentContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public VFSComponent getVfsComponent() {
		return vfsComponent;
	}


	public void setVfsComponent(VFSComponent vfsComponent) {
		this.vfsComponent = vfsComponent;
	}
	
	public EveryCloudBusiness getEveryCloudBusiness() {
		return everyCloudBusiness;
	}

	public void setEveryCloudBusiness(EveryCloudBusiness everyCloudBusiness) {
		this.everyCloudBusiness = everyCloudBusiness;
	}

	
	
	
	
	public String getSqliteUrl(String provider, String userId){
		return "jdbc:sqlite:/" + profilesPath + "/" + provider + "/"+ userId + "/db.sqlite";
	}
	
	public FileObject getProfileDir(String provider, String userId) throws FileSystemException{
		FileObject root = vfsComponent.getFileSystemManager().resolveFile("profiles://");
		return root.resolveFile(provider + "/"+ userId);
	}
	
	
	public ApplicationContext createProfileContext(String provider, String userId) {
		final BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName("org.sqlite.JDBC");
		datasource.setUrl(this.getSqliteUrl(provider, userId));
		
		XmlWebApplicationContext context = new XmlWebApplicationContext();

		//在locatorContext初始化之前添加一个Bean - parentContext
		context.addBeanFactoryPostProcessor(new BeanFactoryPostProcessor(){
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{
				beanFactory.registerSingleton("datasource", datasource);
			}
		});
		
		context.setParent(parentContext);
		context.setServletContext(servletContext);
		context.setConfigLocation("classpath:conf/profile-*.xml");
		
		context.refresh();
		
		
		return context;
	}
	
	public ApplicationContext profileContext(String provider, String userId) {
		final String KEY = provider + userId;
		ApplicationContext ctx = profileContexts.get(KEY);
		if(ctx == null){
			ApplicationContext context = createProfileContext(provider, userId);
			profileContexts.put(KEY, context);
			ctx = context;
		}
		return ctx;
	}


	@Override
	public ProfileInfo getProfile(String provider, String userId) {
		if(!existsProfile(provider, userId))return null;
		
		DatabaseBusiness database = this.getDatabaseBusiness(provider, userId);
		
		ProfileInfo profile = new ProfileInfo();
		profile.setProvider(provider);
		profile.setUserId(userId);
		
		return profile;
	}

	@Override
	public boolean existsProfile(String provider, String userId) {
		try {
			FileObject folder = this.getProfileDir(provider, userId);
			FileObject db = folder.resolveFile("db.sqlite");
			return db != null && db.exists();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public void updateProfile(String provider, String userId) {
		if(!existsProfile(provider, userId))return;

		DatabaseBusiness database = this.getDatabaseBusiness(provider, userId);
		database.update();
	}
	
	@Override
	public void createProfile(String provider, String userId) {
		if(existsProfile(provider, userId))return;

		try {
			FileObject folder = this.getProfileDir(provider, userId);
			folder.createFolder();
			
			DatabaseBusiness database = this.getDatabaseBusiness(provider, userId);
			database.update();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void deleteProfile(String provider, String userId) {
		if(!existsProfile(provider, userId))return;

		try {
			//Close datasource
			BasicDataSource datasource = (BasicDataSource)this.getDataSource(provider, userId);
			datasource.close();
			
			//Close Spring ApplicationContext
			XmlWebApplicationContext ctx = (XmlWebApplicationContext)this.profileContext(provider, userId);
			ctx.close();

			final String KEY = provider + userId;
			profileContexts.remove(KEY);
			
			//Delete profile folder
			FileObject folder = this.getProfileDir(provider, userId);
			folder.delete();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void resetProfile(String provider, String userId) {
		this.deleteProfile(provider, userId);
		this.createProfile(provider, userId);
	}


	@Override
	public AccessToken getAccessToken(String provider, String userId) {
		try {
			FileObject folder = this.getProfileDir(provider, userId);
			FileObject file = folder.resolveFile("access.token");

			//Read file
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(file.getContent().getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while((line = reader.readLine()) != null){
				sb.append(line);
				sb.append("\r");
			}
			reader.close();
			file.close();
			
			String raw = sb.toString();
			
			AccessToken accessToken = everyCloudBusiness.createAccessToken(provider);
			accessToken.load(raw);
			
			return accessToken;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public void saveAccessToken(String provider, String userId, String raw) {
		try {
			FileObject folder = this.getProfileDir(provider, userId);
			FileObject file = folder.resolveFile("access.token");
			
			Writer writer = new OutputStreamWriter(file.getContent().getOutputStream());
			writer.write(raw);
			writer.close();
			file.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



	@Override
	public DatabaseBusiness getDatabaseBusiness(String provider, String userId) {
		ApplicationContext ctx = profileContext(provider, userId);
	    if(ctx == null){
	    	throw new RuntimeException("User profile is null");
		}
		return ctx.getBean("DatabaseBusiness", DatabaseBusiness.class);
	}


	@Override
	public RecordBusiness getRecordBusiness(String provider, String userId) {
		ApplicationContext ctx = profileContext(provider, userId);
	    if(ctx == null){
	    	throw new RuntimeException("User profile is null");
		}
		return ctx.getBean("RecordBusiness", RecordBusiness.class);
	}


	@Override
	public DataSource getDataSource(String provider, String userId) {
		ApplicationContext ctx = profileContext(provider, userId);
	    if(ctx == null){
	    	throw new RuntimeException("User profile is null");
		}
	    return ctx.getBean("datasource", DataSource.class);
	}


}
