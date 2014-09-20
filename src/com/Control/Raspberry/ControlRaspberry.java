package com.Control.Raspberry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class ControlRaspberry {
	Session session;
	ChannelExec channel;
	BufferedReader in;
	Boolean check;
	
	public Boolean ConnectSSH(final String username, final String password, final String IP, final int port)
	{
		check=true;
		new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSch jsch = new JSch();
                    session = jsch.getSession(username,IP,port);
                    session.setPassword(password);
                    Properties config = new Properties();
                    config.put("StrictHostKeyChecking", "no");
                    session.setConfig(config);
                    session.connect();
                    
                    //StartUpdateLoop();
                } catch (final Exception e) {
                    check=false;
                }
            }
        }).start();
        return check;
	}
	public void DisconnectSSH() {
        channel.disconnect();
        session.disconnect();
    }
	 public String ExecuteCommand(String username, String command) {
	        try {
	            if (session.isConnected()) {
	                channel = (ChannelExec) session.openChannel("exec");
	                in = new BufferedReader(new InputStreamReader(channel.getInputStream()));

	                if (!username.equals("root")) {
	                    command = "sudo " + command;
	                }

	                channel.setCommand(command);
	                channel.connect();

	                StringBuilder builder = new StringBuilder();

	                String line = null;
	                while ((line = in.readLine()) != null) {
	                    builder.append(line).append(System.getProperty("line.separator"));
	                }

	                String output = builder.toString();
	                if (output.lastIndexOf("\n") > 0) {
	                    return output.substring(0, output.lastIndexOf("\n"));
	                } else {
	                    return output;
	                }
	            }
	        } catch (Exception e) {
	            
	        }
	        return "";
	    }
}
