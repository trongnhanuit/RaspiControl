package com.android.raspicontrol;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;


public class StreamingFrame extends Fragment {
	
	WebView webview;
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_streaming_frame, container,
				false);
/*				webview=(WebView)rootView.findViewById(R.id.streamview);
=======
				/*webview=(WebView)rootView.findViewById(R.id.streamview);
>>>>>>> origin/master
				String strHtml = "<html><head>"
				          + "</head>"
				          + "<body>"
				          + "<img src='http://192.168.1.101:8080/?action=stream' width='320px' height='500px'/>"            
				          + "</body></html>";
				webview.loadData(strHtml, "text/html", "utf-8");*/
		return rootView;
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) 
	{
	    super.setUserVisibleHint(isVisibleToUser);

	    if (this.isVisible()&& isVisibleToUser) 
        {
            Log.d("MyFragment", "stream");
        }
	}
}
