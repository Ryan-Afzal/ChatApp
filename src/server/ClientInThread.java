package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import encryption.Encryption;
import encryption.EncryptionException;
import message.Message;
import command.Instruction;

@SuppressWarnings("unused")
public class ClientInThread implements Runnable {
	
	private ChatServer server;
	private Socket socket;
	
	private boolean running = true;
	
	public ClientInThread(ChatServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}

	public boolean isRunning() {
    	return this.running;
    }
    
    public void stop() {
    	this.running = false;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
         
            while(!socket.isClosed() && this.server.isRunning()){
            	try {
            		Message input = (Message) in.readObject();
                	if (this.server.banList.contains(input.getID())) {
                		print("[" + input.getTimestamp() + "] " + input.getID() + ": " + input.getText());
                		
            			this.server.log = this.server.log + "\n[" + input.getTimestamp() + "] " + input.getUser() + ": " + input.getText();
            			
                		for(ClientOutThread client : server.getClientIns()){
                			client.addNextMessage(input);
                		}
                	}
            	} catch (ClassNotFoundException e) {
            		System.out.println(e);
            	} catch (SocketException ex) {
            		System.out.println(ex);
            		break;
            	}
            }
            
            in.close();
            this.stop();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void print(String message) {
    	this.server.output(message);
    }
    
    /*public String getDecryptedText(String message) {
		String output = "";
		try {
			output = Encryption.decrypt(message, this.server.getKey());
		} catch (EncryptionException e) {
			output = "WARNING: ENCRYPTION EXCEPTION";
		}
		
		return output;
	}*/

}
