/**
 * 
 */
package com.offact.usedbaron.service;

import java.util.List;

import com.offact.framework.exception.BizException;
import com.offact.usedbaron.vo.CraigslistVO;

/**
 * @author 4530
 *
 */
public interface CraigslistService {

    /**
     * 상품정보 업로드
     * 
     * @param StockMasterVO
     * @return
     * @throws BizException
     */
    public abstract int productUpload(List<CraigslistVO> craigslistList)
    	    throws BizException;
    
	
}
