/**
 * 
 */
package org.zengsource.weixin.web;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zengsource.weixin.ContextImpl;
import org.zengsource.weixin.message.dao.MessageDao;
import org.zengsource.weixin.message.dao.memory.MemoryMessageDao;
import org.zengsource.weixin.message.io.Sender;
import org.zengsource.weixin.message.io.SenderImpl;
import org.zengsource.weixin.process.DefinitionLoader;
import org.zengsource.weixin.process.NodeLoader;
import org.zengsource.weixin.process.dao.DefinitionDao;
import org.zengsource.weixin.process.dao.ExecutionDao;
import org.zengsource.weixin.process.dao.memory.MemoryDefinitionDao;
import org.zengsource.weixin.process.dao.memory.MemoryExecutionDao;
import org.zengsource.weixin.process.impl.ProcessManagerImpl;
import org.zengsource.weixin.process.impl.XmlDefinitionLoader;
import org.zengsource.weixin.user.dao.UserDao;
import org.zengsource.weixin.user.dao.memory.MemoryUserDao;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class WxController implements Serializable {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	private static final String TOKEN = "weixin";

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	private String appId = "wx39acbbc727a861eb";
	private String appSecret = "76f8c0585cbff7d8e146dd514311c61a";

	private Sender sender;
	private UserDao userDao;
	private MessageDao messageDao;
	private ExecutionDao executionDao;
	private DefinitionDao definitionDao;
	private DefinitionLoader definitionLoader;
	private NodeLoader nodeLoader;
	private String[] definitionFiles;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public void doGet( //
			HttpServletRequest request, //
			HttpServletResponse response, //
			String signature, //
			String timestamp, //
			String nonce, //
			String echostr) {

		if (signature.equals(buildSignature(TOKEN, timestamp, nonce))) {
			try {
				logger.info("==> Weixin signature \n" + echostr);
				response.getWriter().print(echostr);
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private String buildSignature(String token, String timestamp, String nonce) {
		List<String> list = new ArrayList<String>();
		list.add(token);
		list.add(timestamp);
		list.add(nonce);
		Collections.sort(list); // 字符串排序

		StringBuffer sb = new StringBuffer();
		for (String str : list) {
			sb.append(str); // 字符串拼接
		}

		return this.encryptBySHA1(sb.toString()); // SHA加密
	}

	private String encryptBySHA1(String src) {
		byte[] bytes = null;
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(src.getBytes("utf-8"));
			bytes = md.digest();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(//
				Integer.toHexString((0x000000ff & bytes[i]) | 0xffffff00).substring(6));
			}
		} catch (Exception ex) {
			return null;
		}
		return sb.toString();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		if (getSender() == null) {
			setSender(new SenderImpl(getAppId(), getAppSecret()));
		}
		if (getDefinitionLoader() == null) {
			XmlDefinitionLoader xmlDefinitionLoader = new XmlDefinitionLoader();
			xmlDefinitionLoader.setNodeLoader(getNodeLoader());
			setDefinitionLoader(xmlDefinitionLoader);
		}
		if (getDefinitionDao() == null) {
			setDefinitionDao(new MemoryDefinitionDao());
		}
		if (getExecutionDao() == null) {
			setExecutionDao(new MemoryExecutionDao());
		}
		if (getMessageDao() == null) {
			setMessageDao(new MemoryMessageDao());
		}
		if (getUserDao() == null) {
			setUserDao(new MemoryUserDao());
		}
		ContextImpl context = ContextImpl.getInstance();
		context.setRequest(request);
		context.setSender(getSender());
		context.setUserDao(getUserDao());
		context.setMessageDao(getMessageDao());
		ProcessManagerImpl processManager = new ProcessManagerImpl();
		processManager.setDefinitionLoader(getDefinitionLoader());
		processManager.setDefintionFiles(getDefinitionFiles());
		processManager.setDefinitionDao(getDefinitionDao());
		processManager.setExecutionDao(getExecutionDao());
		context.setProcessManager(processManager);
		try {
			String xml = context.process();
			if (StringUtils.isNotEmpty(xml)) {
				logger.info("==> Response XML \n" + xml);
			} else {
				xml = "";
				logger.info("==> Response !!EMPTY!! string to invoid duplicated message.");
			}
			response.setContentType("application/xml");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(xml);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public ExecutionDao getExecutionDao() {
		return executionDao;
	}

	public void setExecutionDao(ExecutionDao executionDao) {
		this.executionDao = executionDao;
	}

	public DefinitionDao getDefinitionDao() {
		return definitionDao;
	}

	public void setDefinitionDao(DefinitionDao definitionDao) {
		this.definitionDao = definitionDao;
	}

	public DefinitionLoader getDefinitionLoader() {
		return definitionLoader;
	}

	public void setDefinitionLoader(DefinitionLoader definitionLoader) {
		this.definitionLoader = definitionLoader;
	}

	public NodeLoader getNodeLoader() {
		return nodeLoader;
	}

	public void setNodeLoader(NodeLoader nodeLoader) {
		this.nodeLoader = nodeLoader;
	}

	public String[] getDefinitionFiles() {
		return definitionFiles;
	}

	public void setDefinitionFiles(String[] definitionFiles) {
		this.definitionFiles = definitionFiles;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
