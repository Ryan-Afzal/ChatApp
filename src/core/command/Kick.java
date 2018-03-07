package core.command;

import java.io.Serializable;

import client.Client;

public class Kick implements Instruction, Serializable {

	private static final long serialVersionUID = 1L;
	
	public void run(Client client, String[] args) {
		client.disconnect();
	}

}
