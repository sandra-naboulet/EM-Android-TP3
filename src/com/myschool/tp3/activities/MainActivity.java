package com.myschool.tp3.activities;

import com.myschool.tp3.R;
import com.myschool.tp3.R.id;
import com.myschool.tp3.R.layout;
import com.myschool.tp3.R.menu;
import com.myschool.tp3.R.string;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private final static String PREF_ACTIVE_USER = "ACTIVE_USER";

	TextView mWelcomeTextView = null;
	String mUserName = null;
	String mUserEmail = null;
	String mUserToken = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mWelcomeTextView = (TextView) findViewById(R.id.welcome);
	}

	@Override
	protected void onStart() {

		super.onStart();
	}

	@Override
	protected void onResume() {
		initWithSharedPreferences();
		updateDisplay();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
			alertDialog.setTitle(getResources().getString(R.string.confirmation));
			alertDialog.setMessage(getResources().getString(R.string.confirmation_msg));
			alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.ok),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							leaveTheApp();
						}
					});
			alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.cancel),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alertDialog.show();

		}
		return super.onOptionsItemSelected(item);
	}

	private void leaveTheApp() {
		getSharedPreferences(PREF_ACTIVE_USER, 0).edit().clear().commit();
		finish();
	}

	private void initWithSharedPreferences() {
		mUserName = "";
		SharedPreferences userCurrentLogin = getSharedPreferences(PREF_ACTIVE_USER, 0);
		if (userCurrentLogin != null) {
			mUserEmail = userCurrentLogin.getString("email", null);
			if (mUserEmail != null) {
				SharedPreferences userDatas = getSharedPreferences(mUserEmail, 0);
				mUserName = userDatas.getString("name", "--");
				mUserToken = userDatas.getString("token", "");
			}
		}

		// mUserName = "";

	}

	private void updateDisplay() {
		String welcomeMsg = getResources().getString(R.string.welcome).replace("%s", mUserName);
		mWelcomeTextView.setText(welcomeMsg);
	}

}
