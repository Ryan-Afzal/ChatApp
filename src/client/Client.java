package client;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;

import constraints.Constraints;
import message.Message;
import tools.Tools;
import window.ApplicationWindow;

@SuppressWarnings({ "serial", "unused" })
public class Client extends ApplicationWindow {
    private static final int portNumber = 4444;
    
    private boolean send = false;
    
    private String userName;
    private String serverHost;
    private int serverPort;
    
    private GridBagLayout layout;
    
    private JTextArea textArea;
    private Constraints c_textArea;
    
    private JTextField textField;
    private Constraints c_textField;
    
    //private JTextField keyField;
    //private Constraints c_keyField;
    
    public static void main(String[] args) {
    	String readName = System.getProperty("user.name");
        Client client = new Client(readName, portNumber);
        client.startClient();
    }
    
    private Client (String userName, int portNumber) {
    	super("ChatApp");
    	Tools.setLookAndFeel();
        this.userName = userName;
        this.serverPort = portNumber;
        try {
        	this.serverHost = InetAddress.getByName("51S500036590").getHostAddress();
        } catch (UnknownHostException e) {
        }  
        
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
        		send = true;
        	}
        });
        c_textField = new Constraints(0,2);
        add(textField, c_textField);
        
        /*keyField = new JTextField();
        keyField.setColumns(25);
        c_keyField = new Constraints(0,0);
        add(keyField, c_keyField);*/
        
        pack();
        setVisible(true);
        setResizable(false);
    }
    
    public void output(String message) {
    	this.textArea.setText(this.textArea.getText() + "\n" + message);
    	this.textArea.setCaretPosition(this.textArea.getText().length());
    }

    private void startClient() {
        try{
            Socket socket = new Socket(serverHost, serverPort);
            Thread.sleep(1000);
            ServerInThread serverIn = new ServerInThread(socket, this);
            ServerOutThread serverOut = new ServerOutThread(socket, this);
            Thread serverInThread = new Thread(serverIn);
            Thread serverOutThread = new Thread(serverOut);
            serverInThread.start();
            serverOutThread.start();
            while(this.isRunning() && serverOutThread.isAlive() && serverInThread.isAlive()) {
                if (send) {
                	GregorianCalendar calendar = new GregorianCalendar();
                	String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                	
                	String text = textField.getText();
                	//Do Encryption here
                	
                	Message message = new Message(text, time, this.userName);
                    serverOut.addNextMessage(message);
                    send = false;
                    textField.setText("");
                }
                Thread.sleep(200);
            }
            
            socket.close();
            
        }catch(IOException ex){
            output("Could not connect to server!");
        }catch(InterruptedException ex){
            output("Connection interrupted!");
        }
    }
    
    
    public void setHost(String serverHost) {
    	this.serverHost = serverHost;
    }
    
    public String getUserName() {
    	return this.userName;
    }
    
    /*public byte[] getKey() {
    	int keySize = 16;
    	byte[] key = this.keyField.getText().getBytes();
    	
    	byte[] output = Arrays.copyOf(key, keySize);
    	
    	return output;
    }*/
    
}