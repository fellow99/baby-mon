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
@RequestMapping("/health/record-action")
public class RecordActionCtrl {
	
	@Resource(name="profileBusiness")
	private ProfileBusiness profileBusiness;

	@RequestMapping(params = "method=delete")
	public ModelAndView insertInfo(
			HttpServletRequest request, HttpServletResponse response,
			String type, String id) throws Exception{

		String provider = LoginUserContextHolder.getProvider();
	    UserInfo user = LoginUserContextHolder.getUserInfo();
	    if(user == null){
	      throw new RuntimeException("Session User is null");
	    }
	    
	    String userId = user.getId();

	    RecordBusiness recordBusiness = profileBusiness.getRecordBusiness(provider, userId);
	    
	    recordBusiness.delete(type, id);
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("success", true);
		
		return modelAndView;
	}
	


	@RequestMapping(params = "method=insert")
	public ModelAndView insert(
			HttpServletRequest request, HttpServletResponse response,
			String type, String date, String time, float amount, String info) throws Exception{

		String provider = LoginUserContextHolder.getProvider();
	    UserInfo user = LoginUserContextHolder.getUserInfo();
	    if(user == null){
	      throw new RuntimeException("Session User is null");
	    }
	    
	    String userId = user.getId();

	    RecordBusiness recordBusiness = profileBusiness.getRecordBusiness(provider, userId);
	    
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date recordDate = format.parse(date + " " + time);
		
	    recordBusiness.insert(type, recordDate, amount, info);
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("success", true);
		
		return modelAndView;
	}

}
