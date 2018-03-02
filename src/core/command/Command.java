package core.command;

public interface Command {
	public void run(String[] args);
	public String getTrigger();
	public default String getInfo() {
		return "N/A";
	}
}
