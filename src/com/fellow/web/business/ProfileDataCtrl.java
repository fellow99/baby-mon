package com.fellow.web.business;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fellow.business.ProfileBusiness;
import com.fellow.business.profile.ProfileInfo;
import com.fellow.every.user.UserInfo;
import com.fellow.web.filter.LoginUserContextHolder;

@Controller  
@RequestMapping("/profile/profile-data")
public class ProfileDataCtrl {
	
	@Resource(name="profileBusiness")
	private ProfileBusiness profileBusiness;


	@RequestMapping(params = "method=info")
	public ModelAndView info(
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		String provider = LoginUserContextHolder.getProvider();
	    UserInfo user = LoginUserContextHolder.getUserInfo();
	    if(user == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = user.getId();
	    
	    boolean existsProfile = profileBusiness.existsProfile(provider, userId);
	    ProfileInfo profile = null;
	    if(existsProfile){
	    	profile = profileBusiness.getProfile(provider, userId);
	    }
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("provider", provider);
		modelAndView.addObject("user", user);
		modelAndView.addObject("existsProfile", existsProfile);
		modelAndView.addObject("profile", profile);
		
		return modelAndView;
	}
}
