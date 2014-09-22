package com.android.raspicontrol;

import com.android.control.Function;

import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFrame extends Fragment {

	Button btnConnect;
	TextView tv1, tv2, tv3, tv4;
	EditText ed1, ed2, ed3;
	AlertDialog.Builder mydialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_setting_frame, container,
				false);
		tv1=(TextView)rootView.findViewById(R.id.textView1);
		Typeface face = Typeface.createFromAsset(getActivity().getAssets(),"SEGOEUIL.TTF");
		tv1.setTypeface(face);
		tv2=(TextView)rootView.findViewById(R.id.textView2);
		tv2.setTypeface(face);
		tv3=(TextView)rootView.findViewById(R.id.textView3);
		tv3.setTypeface(face);
		tv4=(TextView)rootView.findViewById(R.id.textView4);
		tv4.setTypeface(face);
		ed1=(EditText)rootView.findViewById(R.id.editText1);
		ed1.setTypeface(face);
		ed2=(EditText)rootView.findViewById(R.id.editText2);
		ed2.setTypeface(face);
		ed3=(EditText)rootView.findViewById(R.id.editText3);
		ed3.setTypeface(face);
		btnConnect = (Button)rootView.findViewById(R.id.btnConnect);
		btnConnect.setTypeface(face);
		btnConnect.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				connect();
			}
		});
		return rootView;
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) 
	{
	    super.setUserVisibleHint(isVisibleToUser);

	    if (this.isVisible()&& isVisibleToUser) 
        {
            Log.d("MyFragment", "setting");
        }
	}
	
	public void connect()
	{
		
		if(Function.ConnectSSH(ed2.getText().toString(), ed3.getText().toString(), ed1.getText().toString()) == 1)
			Toast.makeText(getActivity().getApplicationContext(), "Connect Successfully", Toast.LENGTH_LONG).show();
		else 
		{
			mydialog = new AlertDialog.Builder(getActivity());
			mydialog.setTitle("ERROR");
			mydialog.setMessage("Connect Fail");
			mydialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			mydialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					getActivity().finish();
				}
			});
			mydialog.create().show();
		}
	}
}
