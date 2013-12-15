package com.fellow.web.account;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fellow.business.EveryCloudBusiness;
import com.fellow.every.auth.AccessToken;
import com.fellow.every.user.UserAPI;
import com.fellow.every.user.UserInfo;
import com.fellow.web.filter.LoginUserContextHolder;

@Controller  
@RequestMapping("/account-data")
public class AccountDataCtrl {
	
	@Resource(name="everyCloudBusiness")
	private EveryCloudBusiness everyCloudBusiness;

	@RequestMapping(params = "method=sessionUser")
	public ModelAndView sessionUser(
			HttpServletRequest request, HttpServletResponse response) throws Exception{

	    UserInfo user = LoginUserContextHolder.getUserInfo();
	    if(user == null){
	      throw new RuntimeException("Session User is null");
	    }
    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("data", user);
		
		return modelAndView;
	}
}
