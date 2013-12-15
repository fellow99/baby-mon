package com.fellow.web.business;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fellow.business.ProfileBusiness;
import com.fellow.every.user.UserInfo;
import com.fellow.web.filter.LoginUserContextHolder;

@Controller  
@RequestMapping("/profile/profile-action")
public class ProfileActionCtrl {
	
	@Resource(name="profileBusiness")
	private ProfileBusiness profileBusiness;


	@RequestMapping(params = "method=update")
	public ModelAndView update(
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		String provider = LoginUserContextHolder.getProvider();
	    UserInfo user = LoginUserContextHolder.getUserInfo();
	    if(user == null){
	      throw new RuntimeException("Session User is null");
	    }
	    
	    String userId = user.getId();
	    
	    profileBusiness.updateProfile(provider, userId);
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("success", true);
		
		return modelAndView;
	}


	@RequestMapping(params = "method=create")
	public ModelAndView create(
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		String provider = LoginUserContextHolder.getProvider();
	    UserInfo user = LoginUserContextHolder.getUserInfo();
	    if(user == null){
	      throw new RuntimeException("Session User is null");
	    }
	    
	    String userId = user.getId();
	    
	    profileBusiness.createProfile(provider, userId);
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("success", true);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=delete")
	public ModelAndView delete(
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		String provider = LoginUserContextHolder.getProvider();
	    UserInfo user = LoginUserContextHolder.getUserInfo();
	    if(user == null){
	      throw new RuntimeException("Session User is null");
	    }
	    
	    String userId = user.getId();
	    
	    profileBusiness.deleteProfile(provider, userId);
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("success", true);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=reset")
	public ModelAndView reset(
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		String provider = LoginUserContextHolder.getProvider();
	    UserInfo user = LoginUserContextHolder.getUserInfo();
	    if(user == null){
	      throw new RuntimeException("Session User is null");
	    }
	    
	    String userId = user.getId();
	    
	    profileBusiness.resetProfile(provider, userId);
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("success", true);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=export")
	public ModelAndView export(
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("success", true);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=imports")
	public ModelAndView imports(
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("success", true);
		
		return modelAndView;
	}
}
