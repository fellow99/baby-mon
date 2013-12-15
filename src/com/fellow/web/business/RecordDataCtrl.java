package com.fellow.web.business;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fellow.business.ProfileBusiness;
import com.fellow.every.user.UserInfo;
import com.fellow.profile.RecordBusiness;
import com.fellow.web.filter.LoginUserContextHolder;

@Controller  
@RequestMapping("/health/record-data")
public class RecordDataCtrl {
	
	@Resource(name="profileBusiness")
	private ProfileBusiness profileBusiness;
	


	@RequestMapping(params = "method=today")
	public ModelAndView today(
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		String provider = LoginUserContextHolder.getProvider();
	    UserInfo user = LoginUserContextHolder.getUserInfo();
	    if(user == null){
	      throw new RuntimeException("Session User is null");
	    }
	    
	    String userId = user.getId();

	    RecordBusiness recordBusiness = profileBusiness.getRecordBusiness(provider, userId);
	    
	    long time = new Date().getTime();
	    long d = 1000*60*60*24;
	    Date dateFrom = new Date((time / d - 1) * d);
	    Date dateto = new Date((time / d + 1) * d);
	    List<Map<String, Object>> list = recordBusiness.list(dateFrom, dateto);
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("records", list);
		
		return modelAndView;
	}


	@RequestMapping(params = "method=history")
	public ModelAndView history(
			HttpServletRequest request, HttpServletResponse response,
			String dateFrom, String dateTo, String type) throws Exception{

		String provider = LoginUserContextHolder.getProvider();
	    UserInfo user = LoginUserContextHolder.getUserInfo();
	    if(user == null){
	      throw new RuntimeException("Session User is null");
	    }
	    
	    String userId = user.getId();

	    RecordBusiness recordBusiness = profileBusiness.getRecordBusiness(provider, userId);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
	    List<Map<String, Object>> list = 
	    		(type == null || type.length() == 0 ? 
	    				recordBusiness.list(format.parse(dateFrom), format.parse(dateTo)) : recordBusiness.listByType(type, format.parse(dateFrom), format.parse(dateTo)));
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("records", list);
		
		return modelAndView;
	}
}
