package com.myschool.tp3.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.myschool.tp3.R;
import com.myschool.tp3.VolleyApp;
import com.myschool.tp3.VolleyHelper;
import com.myschool.tp3.models.User;

public class CreateAccountActivity extends Activity implements OnClickListener {

	private final static String PREF_ACTIVE_USER = "ACTIVE_USER";
	private final static String URL = "http://questioncode.fr:10007/api/users";

	Button mCreateAccountButton = null;
	EditText mNameEditText = null;
	EditText mEmailEditText = null;
	EditText mPasswordEditText = null;
	ProgressBar mProgressBar = null;

	SharedPreferences mSharedPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);

		mCreateAccountButton = (Button) findViewById(R.id.create_button);
		mNameEditText = (EditText) findViewById(R.id.create_account_name);
		mEmailEditText = (EditText) findViewById(R.id.create_account_email);
		mPasswordEditText = (EditText) findViewById(R.id.create_account_password);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

		mCreateAccountButton.setOnClickListener(this);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	private boolean hasErrors() {

		if (mNameEditText.getText().toString().trim().isEmpty()) {
			mNameEditText.setError(getResources().getString(R.string.name_error));
			return true;
		}

		if (mEmailEditText.getText().toString().trim().isEmpty()) {
			mEmailEditText.setError(getResources().getString(R.string.email_error));
			return true;
		}

		if (mPasswordEditText.getText().toString().trim().isEmpty()) {
			mPasswordEditText.setError(getResources().getString(R.string.password_error));
			return true;
		}
		return false;
	}

	private void saveInSharedPreferences(String token) {
		String userEmail = mEmailEditText.getText().toString().trim();

		// Save ids
		SharedPreferences userSettings = getSharedPreferences(userEmail, 0);
		SharedPreferences.Editor editor = userSettings.edit();
		editor.putString("name", mNameEditText.getText().toString().trim());
		editor.putString("token", token);

		SharedPreferences userLoginPrefs = getSharedPreferences(PREF_ACTIVE_USER, 0);
		SharedPreferences.Editor editor2 = userLoginPrefs.edit();
		editor2.putString("email", userEmail);

		editor.commit();
		editor2.commit();

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.create_button) {
			if (!hasErrors()) {
				mProgressBar.setVisibility(View.VISIBLE);
				mCreateAccountButton.setVisibility(View.GONE);
				executeRequest();
			}
		}
	}

	private void executeRequest() {
		User user = new User();
		user.setName(mNameEditText.getText().toString().trim());
		user.setEmail(mEmailEditText.getText().toString().trim());
		user.setPassword(mPasswordEditText.getText().toString().trim());

		JsonObjectRequest req = new JsonObjectRequest(Method.POST, URL, user.toCreateAccountJSON(),
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {

						try {
							String token = response.getString("token");
							saveInSharedPreferences(token);
							Toast.makeText(CreateAccountActivity.this, "Vous etes connecté", Toast.LENGTH_LONG).show();
							mProgressBar.setVisibility(View.GONE);
							mCreateAccountButton.setVisibility(View.VISIBLE);
							Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
							startActivity(intent);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						mProgressBar.setVisibility(View.GONE);
						mCreateAccountButton.setVisibility(View.VISIBLE);

						onVolleyError(error);

					}

				});

		VolleyApp.getInstance().addToRequestQueue(req);
	}

	private void onVolleyError(VolleyError error) {
		String errorStr = error.toString();
		NetworkResponse networkResponse = error.networkResponse;

		if (networkResponse != null) {
			errorStr = VolleyHelper.getMessageForStatusCode(this, networkResponse.statusCode);
		} else {
			errorStr = getResources().getString(R.string.error_no_internet);
		}

		Toast.makeText(CreateAccountActivity.this, errorStr, Toast.LENGTH_LONG).show();
	}
}
