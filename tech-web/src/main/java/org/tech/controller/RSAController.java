/**
 * 
 */
package org.tech.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tech.service.impl.RSAService;

/**
 * @author fangyunhe
 * 2017年7月27日下午10:00:58
 */

@RestController
@RequestMapping(value="/rsa")
public class RSAController extends BaseController{
	
	@Resource
	RSAService rsaService;
	
	@RequestMapping(value="/pub",method=RequestMethod.GET)
	public String getPublicKey(){
		return RSAService.PUBLIC_KEY;
	}
}
