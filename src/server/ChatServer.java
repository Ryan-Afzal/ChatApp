package server;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import command.Command;
import constraints.Constraints;
import tools.Tools;
import window.ApplicationWindow;

import message.Message;
import misc.Misc;

public class ChatServer extends ApplicationWindow {
	
	private static final long serialVersionUID = 1L;

	private static final int portNumber = 4444;
    
    private ArrayList<Command> commands;
    
    private int serverPort;
    private String serverHost;
    private List<ClientOutThread> toClients;
    private List<ClientInThread> fromClients;
    ServerSocket serverSocket;
    
    protected String log;
    
    private GridBagLayout layout;
    
    private JTextArea textArea;
    private Constraints c_textArea;
    
    private JTextField textField;
    private Constraints c_textField;
    
    /*private JTextField keyField;
    private Constraints c_keyField;*/
    
    public static void main(String[] args) {
        ChatServer server = new ChatServer(portNumber);
        server.startServer();
    }

    public ChatServer(int portNumber){
    	super("Server " + portNumber);
        this.serverPort = portNumber;
        this.log = "Chat log";
        
        try {
        	this.serverHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        	this.serverHost = "";
        }
        Tools.setLookAndFeel();
        
        initActions();
        
        layout = new GridBagLayout();
    	setLayout(layout);
    	
    	textArea = new JTextArea();
    	textArea.setColumns(75);
    	textArea.setRows(20);
    	c_textArea = new Constraints(0,1);
    	
    	textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setVisible(true);

        JScrollPane scroll = new JScrollPane (textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        add(scroll, c_textArea);
    	
    	textField = new JTextField();
        textField.setColumns(50);
        textField.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		process(textField.getText());
        	}
        });
        c_textField = new Constraints(0,2);
        add(textField, c_textField);
        
        //keyField = new JTextField();
        //keyField.setColumns(25);
        //c_keyField = new Constraints(0,0);
        //add(keyField, c_keyField);
        
        pack();
        setVisible(true);
        setResizable(false);
    }

    public List<ClientInThread> getClientOuts() {
        return fromClients;
    }
    
    public List<ClientOutThread> getClientIns() {
    	return toClients;
    }

    private void startServer(){
        toClients = new ArrayList<ClientOutThread>();
        fromClients = new ArrayList<ClientInThread>();
        serverSocket = null;
        
        try {
        	InetAddress addr = InetAddress.getByName(this.serverHost);
            serverSocket = new ServerSocket(serverPort, 50, addr);
            acceptClients(serverSocket);
        } catch (IOException e){
            output("[ERROR] COULD NOT LISTEN ON PORT: " + serverPort);
            this.stop();
        }
    }
    
    private void acceptClients(ServerSocket serverSocket) {

        output("[BEGIN] SERVER STARTING ON PORT: " + serverSocket.getLocalSocketAddress());
        while (this.isRunning()) {
            try{
                Socket socket = serverSocket.accept();
                output("[ACCEPT] ACCEPTED CLIENT AT: " + socket.getRemoteSocketAddress());
                
                ClientInThread clientIn = new ClientInThread(this, socket);
                ClientOutThread clientOut = new ClientOutThread(this, socket);
                
                Thread clientInThread = new Thread(clientIn);
                Thread clientOutThread = new Thread(clientOut);
                
                clientInThread.start();
                clientOutThread.start();
                
                clientOut.addNextMessage(new Message(this.log, Misc.getTime(), "[SERVER]"));
                
                toClients.add(clientOut);
                fromClients.add(clientIn);
            } catch (IOException ex) {
                output("[ERROR] ACCEPT FAILED ON: " + serverPort);
            }
        }
    }
    
    private void initActions() {
    	this.commands = new ArrayList<Command>();
    	this.commands.add(new Command() {
    		public void run(String[] args) {
    			output("[COMMAND] COMMANDS:");
    			for (int i = 1; i < commands.size(); i++) {
    				output(commands.get(i).getTrigger() + ": " + commands.get(i).getInfo());
    			}
    		}
    		
    		public String getTrigger() {
    			return "-commands";
    		}
    	});
    	this.commands.add(new Command() {
    		public void run(String[] args) {
    			output("[COMMAND] IP: " + serverSocket.getInetAddress().toString());
    		}
    		
    		public String getTrigger() {
    			return "-ip";
    		}
    		
    		public String getInfo() {
    			return "Gives the server's ip address";
    		}
    	});
    	this.commands.add(new Command() {
    		public void run(String[] args) {
    			
    			for (int i = 0; i < fromClients.size(); i++) {
    				ClientInThread client = fromClients.get(i);
    				if (!client.isRunning()) {
    					fromClients.remove(client);
    					toClients.remove(toClients.get(i));
    				}
    			}
    			
    			output("[COMMAND] CLIENTS: " + fromClients.size());
    		}
    		
    		public String getTrigger() {
    			return "-clients";
    		}
    		
    		public String getInfo() {
    			return "Gives the number of connected clients";
    		}
    	});
    	
    }
    
    public boolean process(String command) {
    	String[] commands = command.split(" ");
    	command = commands[0];
    	if (commands.length > 1) {
    		String[] temp = commands;
    		commands = new String[commands.length - 1];
    		for (int i = 1; i < commands.length; i++) {
    			commands[i] = temp[i + 1];
    		}
    	}
    	for (Command c : this.commands) {
    		if (c.getTrigger().equals(command)) {
    			c.run(commands);
    			textField.setText("");
    			return true;
    		}
    	}
    	return false;
    }
    
    public void output(String message) {
    	this.textArea.setText(this.textArea.getText() + "\n" + message);
    	this.textArea.setCaretPosition(this.textArea.getText().length());
    }
    
    public void output(Message message) {
    	this.output(message.toString());
    }
    
    /*public byte[] getKey() {
    	int keySize = 16;
    	byte[] key = this.keyField.getText().getBytes();
    	
    	byte[] output = Arrays.copyOf(key, keySize);
    	
    	return output;
    }*/
    
}