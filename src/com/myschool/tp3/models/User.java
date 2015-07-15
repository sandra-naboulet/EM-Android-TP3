package com.myschool.tp3.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

	private String mName;
	private String mEmail;
	private String mPassword;
	
	public User(){
		
	}

	public User(String mUsername, String mEmail, String mPassword) {
		super();
		this.mName = mUsername;
		this.mEmail = mEmail;
		this.mPassword = mPassword;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mUsername) {
		this.mName = mUsername;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String mPassword) {
		this.mPassword = mPassword;
	}

	public JSONObject toCreateAccountJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("name", mName);
			json.put("email", mEmail);
			json.put("password", mPassword);
		} catch (JSONException e) {

		}
		return json;
	}
	
	public JSONObject toLoginJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("email", mEmail);
			json.put("password", mPassword);
		} catch (JSONException e) {

		}
		return json;
	}

}
