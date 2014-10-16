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
	public static int check;
	static String filecontent="\"\nPORT=\"8080\"\nYUV=\"true\"\nMJPG_STREAMER_DIR=\"\\$(dirname \\$0)\"\nMJPG_STREAMER_BIN=\"mjpg_streamer\"\nLOG_FILE=\"\\${MJPG_STREAMER_DIR}/mjpg-streamer.log\"\nRUNNING_CHECK_INTERVAL=\"2\" # how often to check to make sure the server is running (in seconds)\nHANGING_CHECK_INTERVAL=\"3\" # how often to check to make sure the server is not hanging (in seconds)\nfunction running() {\nif ps aux | grep \\${MJPG_STREAMER_BIN} | grep \\${VIDEO_DEV} >/dev/null 2>&1; then\nreturn 0\nelse\nreturn 1\nfi\n}\nfunction start() {\nif running; then\necho \"already started\"\nreturn 1\nfi\nexport LD_LIBRARY_PATH=\"\\${MJPG_STREAMER_DIR}:.\"\nINPUT_OPTIONS=\"-r \\${RESOLUTION} -d \\${VIDEO_DEV} -f \\${FRAME_RATE}\"\nif [ \"\\${YUV}\" == \"true\" ]; then\nINPUT_OPTIONS+=\" -y\"\nfi\nOUTPUT_OPTIONS=\"-p \\${PORT} -w www\"\n\\${MJPG_STREAMER_DIR}/\\${MJPG_STREAMER_BIN} -i \"input_uvc.so \\${INPUT_OPTIONS}\" -o \"output_http.so \\${OUTPUT_OPTIONS}\" >> \\${LOG_FILE} 2>&1 &\nsleep 1\nif running; then\nif [ \"\\$1\" != \"nocheck\" ]; then\ncheck_running & > /dev/null 2>&1 # start the running checking task\ncheck_hanging & > /dev/null 2>&1 # start the hanging checking task\nfi\necho \"started\"\nreturn 0\nelse\necho \"failed to start\"\nreturn 1\nfi\n}\nfunction stop() {\nif ! running; then\necho \"not running\"\nreturn 1\nfi\nown_pid=\\$\\$\nif [ \"\\$1\" != \"nocheck\" ]; then\n# stop the script running check task\nps aux | grep \\$0 | grep start | tr -s ' ' | cut -d ' ' -f 2 | grep -v \\${own_pid} | xargs -r kill\nsleep 0.5\nfi\n# stop the server\nps aux | grep \\${MJPG_STREAMER_BIN} | grep \\${VIDEO_DEV} | tr -s ' ' | cut -d ' ' -f 2 | grep -v \\${own_pid} | xargs -r kill\necho \"stopped\"\nreturn 0\n}\nfunction check_running() {\necho \"starting running check task\" >> \\${LOG_FILE}\nwhile true; do\nsleep \\${RUNNING_CHECK_INTERVAL}\nif ! running; then\necho \"server stopped, starting\" >> \\${LOG_FILE}\nstart nocheck\nfi\ndone\n}\nfunction check_hanging() {\necho \"starting hanging check task\" >> \\${LOG_FILE}\nwhile true; do\nsleep \\${HANGING_CHECK_INTERVAL}\n# treat the \"error grabbing frames\" case\nif tail -n2 \\${LOG_FILE} | grep -i \"error grabbing frames\" > /dev/null; then\necho \"server is hanging, killing\" >> \\${LOG_FILE}\nstop nocheck\nfi\ndone\n}\nfunction help() {\necho \"Usage: \\$0 [start|stop|restart|status]\"\nreturn 0\n}\nif [ \"\\$1\" == \"start\" ]; then\nstart && exit 0 || exit -1\nelif [ \"\\$1\" == \"stop\" ]; then\nstop && exit 0 || exit -1\nelif [ \"\\$1\" == \"restart\" ]; then\nstop && sleep 1\nstart && exit 0 || exit -1\nelif [ \"\\$1\" == \"status\" ]; then\nif running; then\necho \"running\"\nexit 0\nelse\necho \"stopped\"\nexit 1\nfi\nelse\nhelp\nfi";
	
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
    public static String ExecuteCommand(String command) {
        try {
            if (session.isConnected()) 
            {
                channel = (ChannelExec) session.openChannel("exec");
                channel.setCommand(command);
                channel.connect();
                
                // Get response
/*                BufferedReader reader = new BufferedReader(new InputStreamReader(channel.getInputStream()));
                StringBuilder builder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                
                reader.close();
                return builder.toString();*/
            }
        } catch (Exception e) {
            Log.d("Error",e.getMessage().toString());
        }
        return null;
    }
    
    public static String getfilecontent(String framerate, String resolution)
    {
    	return "\n#!/bin/bash\nVIDEO_DEV=\"/dev/video0\"\nFRAME_RATE=\""+framerate+"\"\nRESOLUTION=\""+resolution+filecontent;
    }
}
