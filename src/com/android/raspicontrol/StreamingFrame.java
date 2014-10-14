package com.android.raspicontrol;

import com.android.control.MjpegInputStream;
import com.android.control.MjpegView;

import android.support.v4.app.Fragment;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class StreamingFrame extends Fragment {
	private MjpegView mv;
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_streaming_frame, container,
				false);
		return rootView;
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) 
	{
	    super.setUserVisibleHint(isVisibleToUser);

	    if (this.isVisible())
	    	if(isVisibleToUser)
	        {
		    	String URL = "http://192.168.2.135:8080/?action=stream"; 
		        
		    	mv = new MjpegView(getActivity()); 
			        
			        //getActivity().addContentView(mv,new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			        mv.setSource(MjpegInputStream.read(URL));
			        mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
			        mv.showFps(true);
			        LinearLayout surface = (LinearLayout)rootView.findViewById(R.id.streamscreen);
			        surface.addView(mv);  
	        }
	    	else
	    	{
	    		//mv.stopPlayback();
	    	}
	}
	
}
