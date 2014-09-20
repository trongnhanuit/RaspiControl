package com.android.raspicontrol;



import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;

public class StreamingFrame extends Fragment {

	WebView webview;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_streaming_frame, container,
				false);
		webview=(WebView)rootView.findViewById(R.id.streamview);
		String strHtml = "<html><head>"
		          + "</head>"
		          + "<body>"
		          + "<img src='http://192.168.1.101:8080/?action=stream'/>"            
		          + "</body></html>";
		webview.loadData(strHtml, "text/html", "utf-8");
		return rootView;
	}
}
