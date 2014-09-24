package com.android.raspicontrol;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class StreamingFrame extends Fragment {
	
	WebView webview;
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_streaming_frame, container,
				false);
		return rootView;
	}
	@SuppressLint("NewApi")
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) 
	{
	    super.setUserVisibleHint(isVisibleToUser);

	    if (this.isVisible()&& isVisibleToUser) 
        {
	    	 Log.d("MyFragment", "stream");
	            webview=(WebView)rootView.findViewById(R.id.streamview);
	            webview.getSettings().setDomStorageEnabled(true);
	            //webview.getSettings().setBuiltInZoomControls(true);
	            webview.getSettings().setJavaScriptEnabled(true);
<<<<<<< HEAD
=======
	            
>>>>>>> origin/master
	            //webview.loadUrl("http://192.168.1.101:8080/");
	            String strHtml = "<html><head>"
					          + "</head>"
					          + "<body>"
					          + "<img src='http://192.168.1.101:8080/?action=stream'/>"            
					          + "</body></html>";
				webview.loadData(strHtml, "text/html", null);
        }
	}
}
