package com.android.control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import android.util.Log;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Function {	
	// Khai bao bien
	static Session session;
	static ChannelExec channel;
	static BufferedReader in;
	static int check;
	
	public static void ConnectSSH(final String username, final String password, final String ip) {
		check=1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSch jsch = new JSch();
                    session = jsch.getSession(username, ip, 22);
                    session.setPassword(password);
                    Properties config = new Properties();
                    config.put("StrictHostKeyChecking", "no");
                    session.setConfig(config);
                    session.connect();
                    Log.d("Info","Connect successfully.");
                } catch (final Exception e) {
                	Log.d("Error",e.getMessage().toString());
                    check=0;
                }
            }
        }).start();
    }

    public static void DisconnectSSH() {
        channel.disconnect();
        session.disconnect();
    }
    public static void ExecuteCommand(String command) {
        try {
            if (session.isConnected()) {
                channel = (ChannelExec) session.openChannel("exec");
                in = new BufferedReader(new InputStreamReader(channel.getInputStream()));

                channel.setCommand(command);
                channel.connect();

                /*StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = in.readLine()) != null) {
                    builder.append(line).append(System.getProperty("line.separator"));
                }
                
                String output = builder.toString();
                if (output.lastIndexOf("\n") > 0) {
                    return output.substring(0, output.lastIndexOf("\n"));
                } else {
                    return output;
                }*/
            }
        } catch (Exception e) {
            Log.d("Error",e.getMessage().toString());
        }
    }
}
