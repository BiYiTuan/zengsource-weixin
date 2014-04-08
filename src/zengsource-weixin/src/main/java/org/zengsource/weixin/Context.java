/**
 * 
 */
package org.zengsource.weixin;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.zengsource.weixin.user.dao.UserDao;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface Context extends Constants, Serializable {
	
	public String process();
	
	public HttpServletRequest getRequest();
	
	public UserDao getUserDao();

}
