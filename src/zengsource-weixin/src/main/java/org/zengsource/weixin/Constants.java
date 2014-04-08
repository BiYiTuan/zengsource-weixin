/**
 * 
 */
package org.zengsource.weixin;

/**
 * 微信提供的所有消息参数名。
 * 
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface Constants {

	// Access Token
	public static final String URL_GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String EXPIRE_IN_SEC = "expires_in";
	public static final String GRANT_TYPE = "grant_type";
	public static final String CLIENT_CREDENTIAL = "client_credential";
	public static final String APP_ID = "appid";
	public static final String SECRET = "secret";
	// 获取用户消息
	public static final String URL_GET_USER = "https://api.weixin.qq.com/cgi-bin/user/info";
	public static final String OPEN_ID = "openid";
	public static final String USER_NICKNAME = "nickname";
	public static final String USER_SEX = "sex";
	public static final String USER_CITY = "city";
	public static final String USER_PROVINCE = "province";
	public static final String USER_COUNTRY = "country";
	public static final String USER_HEAD_IMG_URL = "headimgurl";
	public static final String USER_SUBSCRIBE = "subscribe";
	public static final String USER_SUBSCRIBE_TIME = "subscribe_time";
	// 语言
	public static final String LANG = "lang";
	public static final String LANG_EN = "en";
	public static final String LANG_ZH_CN = "zh_CN";
	public static final String LANG_ZH_TW = "zh_TW";

	// 命令前缀
	public static final char CMD_PREFIX_USER = 'u';
	public static final char CMD_PREFIX_QUESTION = 'q';
	public static final char CMD_PREFIX_ANSWER = 'a';
	// 命令
	public static final int CMD_SET_USERNAME = 100;
	public static final int CMD_SET_PASSWORD = 1014986;
	public static final int CMD_SET_EMAIL = 1023579;
	public static final int CMD_SET_BASE = 101;
	public static final int CMD_SET_EDU = 102;

	public static final int CMD_TEXT_SENT = 201; // 用户发送了文本
	public static final int CMD_IMAGE_SENT = 202; // 用户发送了文本
	public static final int CMD_CANCEL_OPERATION = 0; // 取消操作
	public static final int CMD_CONFIRM_QUESTION = 1; // 确认提问
	public static final int CMD_CONFIRM_SERVICES = 2; // 转发客服消息
	public static final int CMD_QUESTION_CREATED = 203; // 问题已创建
	public static final int CMD_QUESTION_VIEWING = 204; // 正在查看问题
	public static final int CMD_QUESTION_ANSWERING = 205; // 正在回答问题
	public static final int CMD_QUESTION_TO_ANSWER = 206; // 设置要回答的问题

	public static final int CMD_APPLY_FREE_TICKETS = 888; // 申请免费次数

	public static final int CMD_HELP_DESK = 999; // 客服帮助

	// 发送消息
	public static final String MSG_COMMAND_USAGE = "" //
			+ ":( 不知道您是神马意思，输入 " + CMD_HELP_DESK + " 试试看？";
	public static final String MSG_COMMAND_CANCELLED = "" //
			+ "操作已取消。";
	public static final String MSG_USER_NOT_SUBSCRIBED = "" //
			+ ":( 您还没有关注我呢…… ";
	public static final String USER_NAME_NOT_SET = "" //
			+ "请输入要创建或绑定的帐号名（英文和数字）：";
	public static final String USER_NAME_NOT_GOOD = "" //
			+ "错误：该用户名不可用。";
	public static final String USER_NAME_ALREADY_SET = "" //
			+ "帐号已绑定到：%s";
	public static final String USER_PASSWORD_NOT_SET = "" //
			+ "请设置帐号密码（至少6位，建议设置之后删除本地消息记录）：";
	public static final String USER_PASSWORD_NOT_GOOD = "" //
			+ "错误：密码必须是6~15个字母或数字。";
	public static final String USER_PASSWORD_ALREADY_SET = "" //
			+ "密码已设置，修改密码只能在网站上操作。";
	public static final String USER_EMAIL_NOT_SET = "" //
			+ "请设置找回密码的电子邮箱（建议用QQ邮箱）：";
	public static final String USER_EMAIL_NOT_GOOD = "" //
			+ "错误：邮箱格式不对。";
	public static final String USER_EMAIL_ALREADY_SET = "" //
			+ "当前邮箱为：%s";
	public static final String USER_AFTER_BANDING = "" //
			+ "恭喜，帐户已绑定！请点击菜单“帐号-我的帐号”查看帐号信息。";
	public static final String MSG_USER_INFO = "" //
			+ "您有余额：%.2f 元，免费提问次数：%d 次。";
	public static final String MSG_USER_BALANCE = "" //
			+ "您有余额：%.2f 元，免费提问次数：%d 次。";
	public static final String MSG_USER_BASE_NOT_SET = "" //
			+ "提示：您还没有设置基本信息。设置基本信息可以让我们更快地帮您找到合适的老师回答您的问题。\n设置基本信息请输入 " + CMD_SET_BASE;
	public static final String MSG_USER_EDU_NOT_SET = "" //
			+ "提示：您还没有设置教育信息。设置教育信息可以让我们更快地帮您找到合适的老师回答您的问题。\n设置教育信息请输入 " + CMD_SET_EDU;
	public static final String MSG_OK_TO_ASK_QUESTION = "" //
			+ "请发送文字或照片提问：";
	public static final String MSG_OK_TO_ANSWER_QUESTION = "" //
			+ "回答问题 %d，请输入文字或拍照回答问题";
	public static final String MSG_WHICH_QUESTION_TO_ANSWER = "" //
			+ "请输入要回答的问题ID，或者先查看该问题再回答";
	public static final String MSG_BALANCE_NOT_ENOUGH = "" //
			+ "您的免费次数已经用完，余额为0元，请充值后再提问。\n提示：尝试申请更多的免费次数，请输入 " + CMD_APPLY_FREE_TICKETS;

	// 用户提交了消息
	public static final String MSG_TEXT_QUESTION_RECEIVED = "[1/3]系统已收到您的文字消息。\n" //
			+ "输入 " + CMD_CONFIRM_QUESTION + "：确认提问并扣费；\n" //
			+ "输入 " + CMD_CONFIRM_SERVICES + "：免费转给客服；\n" //
			+ "输入 " + CMD_CANCEL_OPERATION + "：取消当前操作。";
	public static final String MSG_TEXT_QUESTION_WRONG_OPTION = "[1/3]选择错误，请重新选择。\n" //
			+ "输入 " + CMD_CONFIRM_QUESTION + "：确认提问并扣费；\n" //
			+ "输入 " + CMD_CONFIRM_SERVICES + "：免费转给客服；\n" //
			+ "输入 " + CMD_CANCEL_OPERATION + ": 取消当前操作。";
	public static final String MSG_IMAGE_QUESTION_RECEIVED = "[1/3]系统已收到您的图片消息。\n" //
			+ "输入 " + CMD_CONFIRM_QUESTION + "：确认提问并扣费；\n" //
			+ "输入 " + CMD_CONFIRM_SERVICES + "：免费转给客服；\n" //
			+ "输入 " + CMD_CANCEL_OPERATION + "：取消当前操作。";
	public static final String MSG_IMAGE_QUESTION_WRONG_OPTION = "[1/3]选择错误，请重新选择。\n" //
			+ "输入 " + CMD_CONFIRM_QUESTION + "：确认提问并扣费；\n" //
			+ "输入 " + CMD_CONFIRM_SERVICES + "：免费转给客服；\n" //
			+ "输入 " + CMD_CANCEL_OPERATION + ": 取消当前操作。";
	public static final String MSG_TO_SET_SUBJECT = "" //
			+ "[2/3]问题已创建。请设置科目，以便我们尽快帮您找到老师解答。";
	public static final String MSG_TEXT_NOT_EXISTED = "" //
			+ "消息不存在，请联系客服。输入：" + CMD_HELP_DESK;
	public static final String MSG_IMAGE_NOT_EXISTED = "" //
			+ "消息不存在，请联系客服。输入：" + CMD_HELP_DESK;
	public static final String MSG_IMAGE_NOT_DOWNLOADED = "" //
			+ "获取图片失败，请联系客服。\n输入：" + CMD_HELP_DESK;
	public static final String MSG_QUESTION_NOT_EXISTED = "" //
			+ "问题不存在，请联系客服。输入：" + CMD_HELP_DESK;
	public static final String MSG_SUBJECT_NOT_EXISTED = "" //
			+ "科目不存在，请输入正确的科目号。";
	public static final String MSG_QUESTION_CREATED = "" //
			+ "[3/3]科目已设置，您的问题正式进入解答流程。老师解答完之后，客服会第一时间发消息通知您。\n随时跟踪问题进展，请发送 Q%s。";
	public static final String MSG_QUESTION_ALREADY_ANSWER = "" //
			+ "您已回答过这个问题，查看或修改您的答案，输入：" + CMD_PREFIX_ANSWER + "%d";
	public static final String MSG_ANSWER_CREATED = "" //
			+ "您的答案已提交，非常感谢您的热心回答！\n" //
			+ "提问者确认后将可能获得报酬。\n" //
			+ "跟踪答案请输入：" + CMD_PREFIX_ANSWER + "%d";
	public static final String MSG_NEW_QUESTION = "" //
			+ "有人提了新问题，请注意响应。\n问题：%s";
	public static final String MSG_NEW_HELP = "" //
			+ "有人发消息给客服，请注意响应。\n内容：%s";
	public static final String MSG_SENT_TO_SERVICES = "" //
			+ "您的消息已发送给客服，请耐心等待回复。";

	public static final String MSG_WELCOME = "" //
			+ "欢迎您！您的帐号已绑定。\n余额：%.0f 元，免费提问次数：%d 次。";
	public static final String MSG_FAREWELL = "" //
			+ "谢谢您使用本服务，欢迎您以后再次关注我们！";

	//

	public static final String QUESTION_TITLE_WX_TEXT = "微信文字问题";
	public static final String QUESTION_TITLE_WX_PICTURE = "微信图片问题";
}
