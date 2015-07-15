package com.myschool.tp3.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class EntryActivity extends Activity {

	private final static String PREF_ACTIVE_USER = "ACTIVE_USER";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onStart() {
		super.onStart();
		checkSharedPreferences();
	}

	private void checkSharedPreferences() {

		SharedPreferences userCurrentLogin = getSharedPreferences(PREF_ACTIVE_USER, 0);
		if (userCurrentLogin != null) {
			String email = userCurrentLogin.getString("email", null);
			if (email != null && !email.isEmpty()) {
				SharedPreferences userDatas = getSharedPreferences(email, 0);
				String user = userDatas.getString("name", null);
				if (user != null && !user.isEmpty()) {
					goToLoginActivity(email);
					return;
				}
			}
		}

		// show menu
		goToMenuActivity();

	}

	private void goToMenuActivity() {
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
	}

	private void goToLoginActivity(String email) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

}
