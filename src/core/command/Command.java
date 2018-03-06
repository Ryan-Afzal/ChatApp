package core.command;

public interface Command {
	public void run(String[] args);
	public String[] getTriggers();
	public default String getInfo() {
		return "N/A";
	}
}
