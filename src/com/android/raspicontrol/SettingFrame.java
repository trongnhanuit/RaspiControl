package com.android.raspicontrol;

import java.util.Calendar;

import com.android.control.Function;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFrame extends Fragment {

	Button btnConnect, btnExcute, btnChange, btnAdd;
	TextView tvConnection, tvIP, tvUsername, tvPassword;
	EditText etIP, etUsername, etPassword, etCommand, etResolution, etFramerate, etWeekday, etMonth, etDay, etHour, etMinute, etMode;
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
		
		etCommand=(EditText)rootView.findViewById(R.id.etCommand);
		btnExcute = (Button)rootView.findViewById(R.id.btnExcute);
		etResolution=(EditText)rootView.findViewById(R.id.etResolution);
		etFramerate=(EditText)rootView.findViewById(R.id.etFramerate);
		btnChange = (Button)rootView.findViewById(R.id.btnChangResolution);
		etWeekday=(EditText)rootView.findViewById(R.id.etWeekday);
		etMonth=(EditText)rootView.findViewById(R.id.etMonth);
		etDay=(EditText)rootView.findViewById(R.id.etDay);
		etHour=(EditText)rootView.findViewById(R.id.etHour);
		etMinute=(EditText)rootView.findViewById(R.id.etMinute);
		etMode=(EditText)rootView.findViewById(R.id.etMode);
		btnAdd = (Button)rootView.findViewById(R.id.btnAdd);
		
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
		
		etIP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus)
					MainActivity.ip=etIP.getText().toString();
			}
		});
		
		btnExcute.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Function.ExecuteCommand(etCommand.getText().toString());
			}
		});
		btnChange.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s="cat <<EOF> /home/pi/mjpg-streamer/mjpg-streamer.sh\n"+Function.getfilecontent(etFramerate.getText().toString(),etResolution.getText().toString());
				Function.ExecuteCommand(s);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Function.ExecuteCommand("cd ~ && cd mjpg-streamer&& ./mjpg-streamer.sh restart");
				Thread thread = new Thread()
				{
				      @Override
				      public void run() {
				          try {
				              while(true) {
				                  sleep(2000);
				                  Function.ExecuteCommand("cd ~ && cd mjpg-streamer&& ./mjpg-streamer.sh start");
				              }
				          } catch (InterruptedException e) {
				          }
				      }
				  };
				thread.start();
			}
		});
		
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//
				// Get current time to set to Raspi
				Calendar calender = Calendar.getInstance(); 
				Function.ExecuteCommand("sudo date -s \""+calender.get(Calendar.DAY_OF_MONTH)+" "+getMonthName(calender.get(Calendar.MONTH)+1)+" "+calender.get(Calendar.YEAR)+" "+calender.get(Calendar.HOUR_OF_DAY)+":"+calender.get(Calendar.MINUTE)+":"+calender.get(Calendar.SECOND)+"\"");
				// Set crontab
				Function.ExecuteCommand("cat <<EOF> mycrontab \nSHELL=/bin/bash\nPATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/home/pi/mjpg-streamer\n"+etMinute.getText().toString()+" "+etHour.getText().toString()+" "+etDay.getText().toString()+" "+etMonth.getText().toString()+" "+etWeekday.getText().toString()+" cd ~ && cd mjpg-streamer&& ./mjpg-streamer.sh "+etMode.getText().toString());
				Function.ExecuteCommand("crontab -u pi ~/mycrontab");
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
	
	String getMonthName(int m)
	{
		switch (m)
		{
			case 1: return "JAN";
			case 2: return "FEB";
			case 3: return "MAR";
			case 4: return "APR";
			case 5: return "MAY";
			case 6: return "JUN";
			case 7: return "JUL";
			case 8: return "AUG";
			case 9: return "SEP";
			case 10: return "OCT";
			case 11: return "NOV";
		}
		return "DEC";
	}
}
