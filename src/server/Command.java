package server;

public interface Command {
	public void run(String[] args);
	public String getTrigger();
}