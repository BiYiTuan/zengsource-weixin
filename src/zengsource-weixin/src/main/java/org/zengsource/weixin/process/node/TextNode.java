/**
 * 
 */
package org.zengsource.weixin.process.node;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zengsource.weixin.message.Message;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class TextNode extends AbstractNode {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static final long serialVersionUID = 1L;

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	Log logger = LogFactory.getLog(getClass());

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Message run(Message in) {
		logger.warn("Msg Content => " + in.getContent());
		return this.onTextReceived(in);
	}

	protected Message onTextReceived(Message in) {
		Message out = in.replyText();
		out.setContent("您的消息已收到！");
		return out;
	}

	@Override
	public String getPrompt() {
		return "";
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
