package com.android.raspicontrol;

import com.android.control.Function;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFrame extends Fragment {

	Button btnConnect;
	TextView tvConnection, tvIP, tvUsername, tvPassword;
	EditText etIP, etUsername, etPassword;
	AlertDialog.Builder mydialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_setting_frame, container,
				false);
		tvConnection=(TextView)rootView.findViewById(R.id.tvConnection);
		Typeface face = Typeface.createFromAsset(getActivity().getAssets(),"SEGOEUIL.TTF");
		tvConnection.setTypeface(face);
		tvIP=(TextView)rootView.findViewById(R.id.tvIP);
		tvIP.setTypeface(face);
		tvUsername=(TextView)rootView.findViewById(R.id.tvUsername);
		tvUsername.setTypeface(face);
		tvPassword=(TextView)rootView.findViewById(R.id.tvPassword);
		tvPassword.setTypeface(face);
		etIP=(EditText)rootView.findViewById(R.id.etIP);
		etIP.setTypeface(face);
		etUsername=(EditText)rootView.findViewById(R.id.etUsername);
		etUsername.setTypeface(face);
		etPassword=(EditText)rootView.findViewById(R.id.etPassword);
		etPassword.setTypeface(face);
		btnConnect = (Button)rootView.findViewById(R.id.btnConnect);
		btnConnect.setTypeface(face);
		btnConnect.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final ProgressDialog pd=ProgressDialog.show(getActivity(), "Please wait", "Login...Please wait!");
				//pd.setCancelable(true);
		         new Thread(new Runnable() 
		         {
		             @Override
		             public void run() 
		             {
		            	 connect();
		                 pd.dismiss();
		             }
		         }).start();
			}
		});
		return rootView;
	}
	
	public void connect()
	{
		Function.ConnectSSH(etUsername.getText().toString(), etPassword.getText().toString(), etIP.getText().toString());
		if( Function.check == 1)
			getActivity().runOnUiThread(new Runnable() 
			{
				  public void run() 
				  {
				    Toast.makeText(getActivity().getBaseContext(), "Connect Successfully", Toast.LENGTH_LONG).show();
				  }
			});
		else 
			getActivity().runOnUiThread(new Runnable() 
			{
				  public void run() 
				  {
					  	mydialog = new AlertDialog.Builder(getActivity());
						mydialog.setTitle("ERROR");
						mydialog.setMessage("Connect Fail");
						mydialog.setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						});
						mydialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								getActivity().finish();
							}
						});
						mydialog.create().show();
				  }
			});
	}
}
