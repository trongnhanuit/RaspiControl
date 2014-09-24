package com.android.control;

import java.util.Properties;
import android.util.Log;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Function {	
	// Khai bao bien
	static Session session;
	static ChannelExec channel;
	public static int check;
	
	public static void ConnectSSH(final String username, final String password, final String ip) {
		check=0;
		try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, ip, 22);
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            check=1;
            Log.d("Info","Connect successfully.");
        } catch (final Exception e) {
        	Log.d("Error",e.getMessage().toString());
        }
	}
	
    public static void DisconnectSSH() {
        channel.disconnect();
        session.disconnect();
    }
    public static void ExecuteCommand(String command) {
        try {
            if (session.isConnected()) 
            {
                channel = (ChannelExec) session.openChannel("exec");
                channel.setCommand(command);
                channel.connect();
            }
        } catch (Exception e) {
            Log.d("Error",e.getMessage().toString());
        }
    }
}
