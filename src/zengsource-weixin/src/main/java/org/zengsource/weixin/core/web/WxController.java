/**
 * 
 */
package org.zengsource.weixin.core.web;

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
import org.zengsource.weixin.core.ContextImpl;
import org.zengsource.weixin.core.ProcessManagerImpl;
import org.zengsource.weixin.core.service.DefinitionService;
import org.zengsource.weixin.core.service.ExecutionService;
import org.zengsource.weixin.core.service.MessageService;
import org.zengsource.weixin.core.service.NodeService;
import org.zengsource.weixin.core.service.UserService;
import org.zengsource.weixin.core.service.impl.DefaultDefinitionService;
import org.zengsource.weixin.core.service.impl.DefaultExecutionService;
import org.zengsource.weixin.core.service.impl.DefaultMessageService;
import org.zengsource.weixin.core.service.impl.DefaultNodeService;
import org.zengsource.weixin.core.service.impl.DefaultUserService;

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

	private UserService userService;
	private NodeService nodeService;
	private MessageService messageService;
	private ExecutionService executionService;
	private DefinitionService definitionService;
	private String[] definitionFiles;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public WxController() {
	}

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
		// if (getSender() == null) {
		// setSender(new SenderImpl(getAppId(), getAppSecret()));
		// }
		// if (getDefinitionLoader() == null) {
		// XmlDefinitionLoader xmlDefinitionLoader = new XmlDefinitionLoader();
		// xmlDefinitionLoader.setNodeLoader(getNodeLoader());
		// setDefinitionLoader(xmlDefinitionLoader);
		// }
		// if (getDefinitionService() == null) {
		// getDefinitionService(new MemoryDefinitionDao());
		// }
		// if (getExecutionDao() == null) {
		// setExecutionDao(new MemoryExecutionDao());
		// }
		// if (getMessageDao() == null) {
		// setMessageDao(new MemoryMessageDao());
		// }
		// if (getUserDao() == null) {
		// setUserDao(new MemoryUserDao());
		// }
		ContextImpl context = ContextImpl.getInstance();
		context.setRequest(request);
		// context.setSender(getSender());
		context.setUserService(getUserService());
		context.setMessageService(getMessageService());
		ProcessManagerImpl processManager = new ProcessManagerImpl();
		// processManager.setDefinitionLoader(getDefinitionLoader());
		processManager.setDefintionFiles(getDefinitionFiles());
		processManager.setDefinitionService(getDefinitionService());
		processManager.setExecutionService(getExecutionService());
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

	public UserService getUserService() {
		if (userService == null) {
			userService = new DefaultUserService();
		}
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public NodeService getNodeService() {
		if (nodeService == null) {
			nodeService = new DefaultNodeService();
		}
		return nodeService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public MessageService getMessageService() {
		if (messageService == null) {
			messageService = new DefaultMessageService();
		}
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public ExecutionService getExecutionService() {
		if (executionService == null) {
			executionService = new DefaultExecutionService();
		}
		return executionService;
	}

	public void setExecutionService(ExecutionService executionService) {
		this.executionService = executionService;
	}

	public DefinitionService getDefinitionService() {
		if (definitionService == null) {
			DefaultDefinitionService dds = new DefaultDefinitionService();
			dds.setNodeService(getNodeService());
			definitionService = dds;
		}
		return definitionService;
	}

	public void setDefinitionService(DefinitionService definitionService) {
		this.definitionService = definitionService;
	}

	public String[] getDefinitionFiles() {
		return definitionFiles;
	}

	public void setDefinitionFiles(String[] definitionFiles) {
		this.definitionFiles = definitionFiles;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
