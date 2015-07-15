package com.myschool.tp3.activities;

import com.myschool.tp3.R;
import com.myschool.tp3.R.id;
import com.myschool.tp3.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity implements OnClickListener {

	Button mCreateAccountButton = null;
	Button mLoginButton = null;

	String mSavedUsername = null;
	String mSavedPassword = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		mCreateAccountButton = (Button) findViewById(R.id.create_account_button);
		mCreateAccountButton.setOnClickListener(this);
		mLoginButton = (Button) findViewById(R.id.login_button);
		mLoginButton.setOnClickListener(this);

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.create_account_button:
			intent = new Intent(this, CreateAccountActivity.class);
			startActivity(intent);
			break;
		case R.id.login_button:
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
