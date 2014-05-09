/**
 * 
 */
package org.zengsource.weixin.core;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.zengsource.weixin.core.service.UserService;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface Context extends Constants, Serializable {
	
	public String process();
	
	public HttpServletRequest getRequest();
	
	public UserService getUserService();

}
