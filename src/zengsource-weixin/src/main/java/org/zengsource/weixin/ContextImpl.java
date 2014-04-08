/**
 * 
 */
package org.zengsource.weixin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.zengsource.weixin.message.Message;
import org.zengsource.weixin.message.dao.MessageDao;
import org.zengsource.weixin.message.io.Sender;
import org.zengsource.weixin.process.ProcessManager;
import org.zengsource.weixin.session.SessionImpl;
import org.zengsource.weixin.user.User;
import org.zengsource.weixin.user.dao.UserDao;

/**
 * <img src="org/zengsource/weixin/classes.png" />
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
public class ContextImpl implements Context {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	private static ThreadLocal<ContextImpl> instance;

	public static ContextImpl getInstance() {
		if (instance == null) {
			instance = new ThreadLocal<ContextImpl>();
		}
		if (instance.get() == null) {
			instance.set(new ContextImpl());
		}
		return instance.get();
	}

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	private HttpServletRequest request;

	private Sender sender;

	private UserDao userDao;

	private MessageDao messageDao;

	private ProcessManager processManager;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public ContextImpl() {
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public String process() {
		SAXReader saxReader = new SAXReader();
		try {
			Document doc = saxReader.read(request.getInputStream());
			String xmlIn = (doc == null) ? "" : doc.asXML();
			logger.info("==> Weixin request \n" + xmlIn);
			// 解析消息
			Message messageIn = Message.fromXml(xmlIn);
			// 检查消息是否为重发
			Message messageOld = getMessageDao().queryLast(messageIn);
			if (messageOld != null && messageOld.isDuplicated(messageIn)) {
				return ""; // 属于重发消息，直接返回空字符串
			}
			// 保存接收到的消息
			messageIn = getMessageDao().insert(messageIn);
			if (messageIn != null) {
				logger.info("=> New message saved! | " + messageIn.toString());
			}
			// 检查用户
			User user = null;
			if (getUserDao() != null) {
				user = getUserDao().query(messageIn.getFromUserName());
				if (user == null) {
					user = getSender().getUser(messageIn);
					if (user != null) {
						getUserDao().insert(user);
						logger.info("=> New user saved | " + user.getOpenId());
					}
				}
			}
			// 处理消息
			SessionImpl session = new SessionImpl();
			session.setMessage(messageIn);
			session.setUser(user);
			session.setProcessManager(processManager);
			Message messageOut = session.execute();
			// 保存要回复的消息
			messageOut = getMessageDao().insert(messageOut); 
			if (messageOut != null) { // 返回消息
				logger.info("=> Response message saved! | " + messageOut.toString());
				return messageOut.toXml();
			}
		} catch (DocumentException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}

		return null;
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public ProcessManager getProcessManager() {
		return processManager;
	}

	public void setProcessManager(ProcessManager processManager) {
		this.processManager = processManager;
	}

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
