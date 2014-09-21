package com.android.raspicontrol;

import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFrame extends Fragment {
	TextView tv01, tv02, tv03, tv04, tv05, tv06, tv07, tv08;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_about_frame, container,
				false);
		Typeface tface=Typeface.createFromAsset(getActivity().getAssets(), "SEGOEUIL.TTF");
		tv01=(TextView)rootView.findViewById(R.id.tv01);
		tv02=(TextView)rootView.findViewById(R.id.tv02);
		tv03=(TextView)rootView.findViewById(R.id.tv03);
		tv04=(TextView)rootView.findViewById(R.id.tv04);
		tv05=(TextView)rootView.findViewById(R.id.tv05);
		tv06=(TextView)rootView.findViewById(R.id.tv06);
		tv07=(TextView)rootView.findViewById(R.id.tv07);
		tv08=(TextView)rootView.findViewById(R.id.tv08);
		
		tv01.setTypeface(tface);
		tv02.setTypeface(tface);
		tv03.setTypeface(tface);
		tv04.setTypeface(tface);
		tv05.setTypeface(tface);
		tv06.setTypeface(tface);
		tv07.setTypeface(tface);
		tv08.setTypeface(tface);
		return rootView;
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) 
	{
	    super.setUserVisibleHint(isVisibleToUser);

	    if (this.isVisible()&& isVisibleToUser) 
        {
            Log.d("MyFragment", "About");
        }
	}
}
