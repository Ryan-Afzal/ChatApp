package core.command;

import java.io.Serializable;

import client.Client;

public interface Instruction extends Serializable {
	public void run(Client client, String[] args);
}
