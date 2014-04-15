/**
 * 
 */
package org.zengsource.weixin.process.node;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface ResponseMessages {
	// 执行后提示信息
	public static final String INFO_WELCOME_TO_SUBSTRIBE = "谢谢订阅！";
	public static final String INFO_CLICK_MENU = "您点击了%s。";
	public static final String INFO_APPLICATION_RECEIVED = "您的申请已经收到。";
	public static final String INFO_APPLICATION_REASON_SAVED = "您的申请原因已收到，小编会仔细阅读并给予回复，谢谢！";
	public static final String INFO_APPLICATION_RECEIVED_AGAIN = "您上次的申请还在审批中，请耐心等候。";
	// 执行前提示信息
	public static final String PROMPT_APPLICATION_REASON = "请填写或更新能够打动小编的申请原因，谢谢！";
	// 通知信息
	public static final String NOTICE_NEW_APPLICATION = "有一位用户申请免费提问次数，请及时响应。";

	public static final String ERROR_UNKNOWN_OPERATION = "我不明白您的意思！";

}
