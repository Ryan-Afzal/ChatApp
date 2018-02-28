package message;

import java.io.Serializable;

/**
 * General 'data packet' class, used to store a message for transmission.
 * @author s-afzalr
 */
public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String text;
	private String timestamp;
	private String user;
	private Object attachment;
	private AttachmentType attachment_type;
	
	/**
	 * Creates a new Message, with the specified text, timestamp, and user, and a {@code null} attachment.
	 * @param text The text of the message.
	 * @param timestamp The time at which the message was sent.
	 * @param user The user who sent the message.
	 */
	public Message(String text, String timestamp, String user) {
		this(text, timestamp, user, null, AttachmentType.NONE);
	}
	
	public Message(String text, String timestamp, String user, Object attachment, AttachmentType attachment_type) {
		super();
		this.text = text;
		this.timestamp = timestamp;
		this.user = user;
		this.attachment = attachment;
		this.attachment_type = attachment_type;
	}
	
	public Message() {
		this("NO MESSAGE", "?", "<?>");
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getTimestamp() {
		return this.timestamp;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String toString() {
		return "[" + getTimestamp() + "] " + getUser() + ": " + getText();
	}
	
	public boolean hasAttachment() {
		if (!(this.attachment_type).equals("none")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Object getAttachment() {
		return this.attachment;
	}
	
	public AttachmentType getAttachmentType() {
		return this.attachment_type;
	}

}
