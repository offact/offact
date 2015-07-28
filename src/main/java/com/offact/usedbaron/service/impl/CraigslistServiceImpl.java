package com.offact.usedbaron.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offact.framework.db.SqlSessionCommonAdminDao;
import com.offact.framework.db.SqlSessionCommonDao;
import com.offact.framework.exception.BizException;
import com.offact.usedbaron.service.CraigslistService;
import com.offact.usedbaron.vo.CraigslistVO;

/**
 * @author 4530
 *
 */
@Service
public class CraigslistServiceImpl implements CraigslistService {

	private final Logger logger = Logger.getLogger(getClass());
	 
	@Autowired
	private SqlSessionCommonDao commonDao;

	@Autowired
	private SqlSessionCommonAdminDao commonSubDao;		//multi datasource test

	public int productUpload(List<CraigslistVO> craigslistList) throws BizException
    	  {
    	   
    	    int result = 0;

    	    for (int i = 0; i < craigslistList.size(); i++) {

    	    	CraigslistVO craigslistVO = (CraigslistVO)craigslistList.get(i);
  
      	      try 
      	      {
      	    	result=this.commonDao.insert("Craigslist.insertProduct", craigslistVO);

      	      } catch (Exception e) {
	    
      	    	  e.printStackTrace();
      	      }
	
    	    }

	    return result;
	  }


}
