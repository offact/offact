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

public class HomeController {

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

	@RequestMapping("/hello")
	 public @ResponseBody
	 String hello(@RequestParam(value = "name") String name,
				  @RequestParam(value = "gender") String gender,
				  @RequestParam(value = "email") String email,
				  @RequestParam(value = "phone") String phone,
				  @RequestParam(value = "city") String city) 
	{

		  System.out.println(name);
		  System.out.println(gender);
		  System.out.println(email);
		  System.out.println(phone);
		  System.out.println(city);

//		  String str = "{\"user\": { \"name\": \"" + name + "\",\"gender\": \""
//		    + gender + "\",\"email\": \"" + email + "\",\"phone\": \""
//		    + phone + "\",\"city\": \"" + city + "\"}}";

		  String str = name;

		  return str;

	 }
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws BizException
	 */
	@RequestMapping(value = "/errors404", method = RequestMethod.GET)
	public ModelAndView errors404(HttpServletRequest request,
			                      HttpServletResponse response) throws BizException 
	{
		logger.info("errors404");
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("errors/404");
		return mv;
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws BizException
	 */
	@RequestMapping(value = "/errors500", method = RequestMethod.GET)
	public ModelAndView errors500(HttpServletRequest request,
			                      HttpServletResponse response) throws BizException 
	{
		
		logger.info("errors500");
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("errors/500");
		return mv;
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws BizException
	 */
	@RequestMapping(value = "/errors", method = RequestMethod.GET)
	public ModelAndView errors(HttpServletRequest request,
			                   HttpServletResponse response) throws BizException 
	{
		
		logger.info("errors");
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("errors/errors");
		return mv;
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws BizException
	 */
	@RequestMapping(value = "/warning", method = RequestMethod.GET)
	public ModelAndView warning(HttpServletRequest request,
			                    HttpServletResponse response) throws BizException 
	{
		
		logger.info("warning");
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("errors/warning");
		return mv;
	}
	//동작
	@RequestMapping(value = "/redirectUrl2", method = RequestMethod.GET)
	public ModelAndView redirectUrl2()
	{
		return new ModelAndView("redirect:/index");
	}

	//동작
	@RequestMapping(value = "/redirectUrl3", method = RequestMethod.GET)
	public ModelAndView redirectUrl3()
	{
		
		RedirectView redirectView = new RedirectView("/index");
		redirectView.setContextRelative(true);

		Map<String, ?> map = null;
		ModelAndView mv = new ModelAndView(redirectView, map);
		return mv;
	}

	//동작
	@RequestMapping(value = "/redirectUrl4", method = RequestMethod.GET)
	public ModelAndView redirectUrl4()
	{
		
		ModelAndView mv = new ModelAndView();

		mv.setView(new RedirectView("/addys/index"));
		Object params = null;
		mv.addObject("parameter",params);
		return mv;
	}

	//동작
	@RequestMapping(value = "/redirectUrl5", method = RequestMethod.POST)
	public ModelAndView redirectUrl5()
	{
		
		ModelAndView mv = new ModelAndView();

		mv.setView(new RedirectView("/addys/index"));
		Object params = null;
		mv.addObject("parameter",params);
		return mv;
	}
	
}
