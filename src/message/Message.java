package message;

import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String text;
	private String timestamp;
	private String user;
	private Object attachment;
	private String attachment_type;
	
	public Message(String text, String timestamp, String user) {
		super();
		this.text = text;
		this.timestamp = timestamp;
		this.user = user;
		this.attachment = null;
		this.attachment_type = "none";
	}
	
	public Message(String text, String timestamp, String user, Object attachment, String attachment_type) {
		this(text, timestamp, user);
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
	
	public String getAttachmentType() {
		return this.attachment_type;
	}

}
