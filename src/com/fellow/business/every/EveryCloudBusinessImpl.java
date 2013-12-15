package com.fellow.business.every;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.scribe.builder.api.Api;
import org.scribe.model.OAuthConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.fellow.business.EveryCloudBusiness;
import com.fellow.every.auth.AccessToken;
import com.fellow.every.user.UserAPI;

@Service
public class EveryCloudBusinessImpl implements EveryCloudBusiness, ApplicationContextAware{

	private ApplicationContext parentContext;
	private ServletContext servletContext;

	private Map<String, ApplicationContext> profileContexts;
	
	public EveryCloudBusinessImpl(){
		profileContexts = new ConcurrentHashMap<String, ApplicationContext>();
	}
	
	@Override
	public void setApplicationContext(ApplicationContext parentContext)
			throws BeansException {
		this.parentContext = parentContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public ApplicationContext createProfileContext(String provider) {
		XmlWebApplicationContext context = new XmlWebApplicationContext();

		context.setParent(parentContext);
		context.setServletContext(servletContext);
		context.setConfigLocation("classpath:conf/api-service-" + provider + ".xml");
		
		context.refresh();
		
		
		return context;
	}
	
	public ApplicationContext profileContext(String provider) {
		ApplicationContext ctx = profileContexts.get(provider);
		if(ctx == null){
			ApplicationContext context = createProfileContext(provider);
			profileContexts.put(provider, context);
			ctx = context;
		}
		return ctx;
	}

	@Override
	public OAuthConfig getOAuthConfig(String provider) {
		ApplicationContext ctx = profileContext(provider);
	    if(ctx == null){
	    	throw new RuntimeException("Provider is null");
		}
	    return ctx.getBean("oauthConfig", OAuthConfig.class);
	}

	@Override
	public Api getOAuthAPI(String provider) {
		ApplicationContext ctx = profileContext(provider);
	    if(ctx == null){
	    	throw new RuntimeException("Provider is null");
		}
	    return ctx.getBean("oauth2API", Api.class);
	}

	@Override
	public UserAPI getUserAPI(String provider) {
		ApplicationContext ctx = profileContext(provider);
	    if(ctx == null){
	    	throw new RuntimeException("Provider is null");
		}
	    return ctx.getBean("userAPI", UserAPI.class);
	}

	@Override
	public AccessToken createAccessToken(String provider) {
		ApplicationContext ctx = profileContext(provider);
	    if(ctx == null){
	    	throw new RuntimeException("Provider is null");
		}
	    
	    AccessTokenFactory accessTokenFactory = ctx.getBean("accessTokenFactory", AccessTokenFactory.class);
	    if(accessTokenFactory == null){
	    	throw new RuntimeException("AccessTokenFactory object is null");
		}
	    
	    return accessTokenFactory.createAccessToken();
	}
}
