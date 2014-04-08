/**
 * 
 */
package org.zengsource.weixin.message.dao.memory;

import java.util.ArrayList;
import java.util.List;

import org.zengsource.weixin.message.Message;
import org.zengsource.weixin.message.dao.MessageDao;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public class MemoryMessageDao implements MessageDao {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private static List<Message> memoryMessages;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public MemoryMessageDao() {
		memoryMessages = new ArrayList<Message>();
	}

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Override
	public Message queryLast(Message message) {
		int total = memoryMessages.size();
		for (int i = total - 1; i >= 0;) {
			if (memoryMessages.get(i).getFromUserName().equals(message.getFromUserName())) {
				return memoryMessages.get(i);
			}
		}
		return null;
	}
	
	@Override
	public Message insert(Message message) {
		if (message != null) {
			memoryMessages.add(message);
		}
		return message;
	}
	
	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
