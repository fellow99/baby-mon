package com.fellow.web.business;

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
@RequestMapping("/health/remind-data")
public class RemindDataCtrl {
	
	@Resource(name="profileBusiness")
	private ProfileBusiness profileBusiness;

	@RequestMapping(params = "method=last")
	public ModelAndView last(
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		String provider = LoginUserContextHolder.getProvider();
	    UserInfo user = LoginUserContextHolder.getUserInfo();
	    if(user == null){
	      throw new RuntimeException("Session User is null");
	    }
	    
	    String userId = user.getId();

	    RecordBusiness recordBusiness = profileBusiness.getRecordBusiness(provider, userId);
	    
	    Map<String, Object> lastMilk = recordBusiness.last("milk");
	    Map<String, Object> lastJuice = recordBusiness.last("juice");
	    Map<String, Object> lastShit = recordBusiness.last("shit");
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("lastMilk", lastMilk);
		modelAndView.addObject("lastJuice", lastJuice);
		modelAndView.addObject("lastShit", lastShit);
		
		return modelAndView;
	}


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
	    Date date = new Date((time / d) * d);
	    Float sumMilk = recordBusiness.sum("milk", date, date);
	    Float sumJuice = recordBusiness.sum("juice", date, date);
	    Float sumShit = recordBusiness.sum("shit", date, date);
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("sumMilk", sumMilk);
		modelAndView.addObject("sumJuice", sumJuice);
		modelAndView.addObject("sumShit", sumShit);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=yesterday")
	public ModelAndView yesterday(
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
	    Date date = new Date((time / d - 1) * d);
	    Float sumMilk = recordBusiness.sum("milk", date, date);
	    Float sumJuice = recordBusiness.sum("juice", date, date);
	    Float sumShit = recordBusiness.sum("shit", date, date);
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("sumMilk", sumMilk);
		modelAndView.addObject("sumJuice", sumJuice);
		modelAndView.addObject("sumShit", sumShit);
		
		return modelAndView;
	}
}
