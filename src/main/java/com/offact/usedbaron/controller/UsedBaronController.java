package com.offact.usedbaron.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.offact.usedbaron.service.UserService;
import com.offact.usedbaron.vo.UserVO;
import com.offact.framework.constants.CodeConstant;
import com.offact.framework.exception.BizException;
import com.offact.framework.jsonrpc.JSONRpcService;
import com.offact.framework.util.StringUtil;

/**
 * Handles requests for the application home page.
 */
@Controller

public class UsedBaronController {

	private final Logger logger = Logger.getLogger(getClass());
	/*
    * log id 생성 
    */
	public String logid(){
		
		double id=Math.random();
		long t1 = System.currentTimeMillis ( ); 
		
		String logid=""+t1+id;
		
		return logid;
	}
	
	@Autowired
	private UserService userSvc;
	
	@Value("#{config['offact.host.url']}")
	private String host_url;

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws BizException
	 */
	@RequestMapping(value = "/usedbaron/loginform", method = RequestMethod.GET)
	public ModelAndView usedBaronLoginForm(HttpServletRequest request,
                                 HttpServletResponse response) throws BizException 
	{
		
		logger.info("usedbaron loginform");
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("usedbaron/loginForm");
		return mv;
	}
	
	/**
	 * Login 처리
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/usedbaron/login")
	public ModelAndView usedBaronLogin(HttpServletRequest request,
			                       HttpServletResponse response) throws Exception
	{
		
		//log Controller execute time start
		String logid=logid();
		long t1 = System.currentTimeMillis();
		logger.info("["+logid+"] Controller start");
		
		ModelAndView  mv = new ModelAndView();

		String strUserId = StringUtil.nvl(request.getParameter("id"));
		String strUserPw = StringUtil.nvl(request.getParameter("pwd"));
		String strPassword ="";
		
		logger.info(">>>> userId :"+strUserId);
		logger.info(">>>> userPw :"+strUserPw);
		
		
		String strMainUrl = "";
		
		// # 2. 넘겨받은 아이디로 데이터베이스를 조회하여 사용자가 있는지를 체크한다.
		UserVO userChkVo = new UserVO();
		userChkVo.setUserId(strUserId);
		userChkVo.setInPassword(strUserPw);
		UserVO userChk = userSvc.getUser(userChkVo);		

		String strUserName = "";
		
		String ip = request.getRemoteAddr(); 
		logger.info(">>>> RemoteAddr :"+ip);

		if(userChk != null)
		{
			//패스워드 체크
			if(!userChk.getPassword().equals(userChk.getInPassword())){
				
				logger.info(">>> 비밀번호 오류");
				strMainUrl = "usedbaron/loginFail";
				
				mv.addObject("userId", strUserId);
				
				mv.setViewName(strMainUrl);
				
				//log Controller execute time end
		      	long t2 = System.currentTimeMillis();
		      	logger.info("["+logid+"] Controller end execute time:[" + (t2-t1)/1000.0 + "] seconds");
		      	
				return mv;
				
			}
			
			strUserId= userChk.getUserId();
			strUserName = userChk.getUserName();
			strPassword = userChk.getPassword();

			// # 3. Session 객체에 셋팅
			
			HttpSession session = request.getSession(false);
			
			if(session != null)
			{
				session.invalidate();
			}
				
				session = request.getSession(true);
				session.setAttribute("strUserId", strUserId);
				session.setAttribute("strUserName", strUserName);
	
				mv.addObject("userId", strUserId);

				strMainUrl = "usedbaron/map";
				
			} else {//app 상요자 정보가 없는경우
	
				logger.info(">>>사용자 정보 없음");
				strMainUrl = "usedbaron/loginFail";
			}
			
			mv.addObject("userId", strUserId);
			
			mv.setViewName(strMainUrl);
			
			//log Controller execute time end
	      	long t2 = System.currentTimeMillis();
	      	logger.info("["+logid+"] Controller end execute time:[" + (t2-t1)/1000.0 + "] seconds");
	      	
			return mv;
		}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws BizException
	 */
	@RequestMapping(value = "usedbaron/main", method = RequestMethod.GET)
	public ModelAndView usedBaronMain(HttpServletRequest request,
                                 HttpServletResponse response) throws BizException 
	{
		
		logger.info("main");
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("usedbaron/main");
		return mv;
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws BizException
	 */
	@RequestMapping(value = "/usedbaron/map", method = RequestMethod.GET)
	public ModelAndView usedBaronMap(HttpServletRequest request,
                                 HttpServletResponse response) throws BizException 
	{
		
		logger.info("usedBaronMap");
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("usedbaron/map");
		return mv;
	}
	/**
	   * hybridLogin
	   *
	   * @param stockVO
	   * @param request
	   * @param response
	   * @param model
	   * @param locale
	   * @return
	   * @throws BizException
	   */
	  @RequestMapping(value = "/usedbaron/hybridlogin", method = RequestMethod.POST)
	  public @ResponseBody
	  String hybridLogin(String email, 
			  			 String pwd, 
	  		             HttpServletRequest request, 
	  		             HttpServletResponse response) throws BizException
	  {
	  	//log Controller execute time start
			String logid=logid();
			long t1 = System.currentTimeMillis();
			logger.info("["+logid+"] Controller start : email" + email);
			
			String loginResult="F";
			String strPassword ="";
			
			logger.info(">>>> strEmail :"+email);
			logger.info(">>>> strUserPw :"+pwd);
			
			
			String strMainUrl = "";
			
			// # 2. 넘겨받은 아이디로 데이터베이스를 조회하여 사용자가 있는지를 체크한다.
			UserVO userChkVo = new UserVO();
			userChkVo.setEmail(email);
			userChkVo.setInPassword(pwd);
			UserVO userChk = userSvc.getUser(userChkVo);		

			String strUserName = "";
			
			String ip = request.getRemoteAddr(); 
			logger.info(">>>> RemoteAddr :"+ip);

			if(userChk != null)
			{
				//패스워드 체크
				if(!userChk.getPassword().equals(userChk.getInPassword())){
					
					logger.info(">>> 비밀번호 오류");
					loginResult="F";
					
					//log Controller execute time end
			      	long t2 = System.currentTimeMillis();
			      	logger.info("["+logid+"] Controller end execute time:[" + (t2-t1)/1000.0 + "] seconds");
			      	
					return loginResult;
					
				}
				
				loginResult="S";
				
			}
			//log Controller execute time end
	     	long t2 = System.currentTimeMillis();
	     	logger.info("["+logid+"] Controller end execute time:[" + (t2-t1)/1000.0 + "] seconds");

	     	return loginResult;
	  } 	
	  
}
