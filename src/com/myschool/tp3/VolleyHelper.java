package com.myschool.tp3;

import android.content.Context;

public class VolleyHelper {

	public static String getMessageForStatusCode(Context context, int code) {

		switch (code) {
		case 401:
			return context.getResources().getString(R.string.error_unauthorized);
		case 422:
			return context.getResources().getString(R.string.error_user_already_exists);
		case 500:
			return context.getResources().getString(R.string.error_server_unvailable);
		default:
			return context.getResources().getString(R.string.error_unknown);
		}

	}
}
