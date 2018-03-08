package core.command;

import java.util.ArrayList;

public final class CommandRegistrar {

	private ArrayList<Command> commands;
	
	public CommandRegistrar() {
		this.commands = new ArrayList<>();
	}
	
	public void addCommand(Command command) {
		this.commands.add(command);
	}
	
	public void removeCommand(Command command) {
		this.commands.remove(command);
	}
	
}
