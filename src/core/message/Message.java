package core.message;

import java.io.Serializable;

import core.misc.Misc;
import core.misc.Nullable;

/**
 * General 'data packet' class, used to store a message for transmission.
 * @author s-afzalr
 */
public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String text;
	private String timestamp;
	private String user;
	private String id;
	private Serializable attachment;
	private AttachmentType attachment_type;
	private String[] args;
	
	/**
	 * Creates a {@code new} Message, with the specified text, timestamp, and user, and a {@code null} attachment.
	 * @param text The text of the message.
	 * @param timestamp The time at which the message was sent.
	 * @param user The user who sent the message.
	 * @param id The id of the user who sent the message.
	 */
	public Message(String text, String timestamp, String user, String id) {
		this(text, timestamp, user, id, null, AttachmentType.NONE, null);
	}
	
	/**
	 * Creates a {@code new} Message, with the specified text, timestamp, and user, attachement, and attachment type.
	 * @param text The text of the message.
	 * @param timestamp The time at which the message was sent.
	 * @param user The user who sent the message.
	 * @param id The id of the user who sent the message.
	 * @param attachment The attachment of the message.
	 * @param attachment_type The type of the attachment.
	 * @param args Arguments passed to an attachment.
	 */
	public Message(String text, String timestamp, String user, String id, Serializable attachment, AttachmentType attachment_type, @Nullable String[] args) {
		super();
		this.text = text;
		this.timestamp = timestamp;
		this.user = user;
		this.id = id;
		this.attachment = attachment;
		this.attachment_type = attachment_type;
		this.args = args;
	}
	
	/**
	 * Creates an {@code new} Message with a default text, timestamp, and user.
	 */
	public Message() {
		this("NO MESSAGE", Misc.getTime(), "<?>", "<?>");
	}
	
	/**
	 * Gets the text of the message.
	 * @return Returns the text of the message.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Gets the timestamp of the message.
	 * @return Returns the timestamp of the message.
	 */
	public String getTimestamp() {
		return this.timestamp;
	}
	
	/**
	 * Gets the user who sent the message.
	 * @return Returns the user who sent the message.
	 */
	public String getUser() {
		return this.user;
	}
	
	@Override
	public String toString() {
		return "[" + getTimestamp() + "] " + getUser() + ": " + getText();
	}
	
	/**
	 * Tells whether the message has an attachment.
	 * @return Returns true if the message has an attachment, and false otherwise.
	 */
	public boolean hasAttachment() {
		if (!(this.attachment_type.equals(AttachmentType.NONE))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Gets the attachment of the message.
	 * @return Returns the attachment of the message.
	 */
	public Serializable getAttachment() {
		return this.attachment;
	}
	
	/**
	 * Gets the attachment type of the message.
	 * @return Returns the attachment type of the message.
	 */
	public AttachmentType getAttachmentType() {
		return this.attachment_type;
	}
	
	public String getID() {
		return this.id;
	}

	public String[] getArgs() {
		return args;
	}

}
