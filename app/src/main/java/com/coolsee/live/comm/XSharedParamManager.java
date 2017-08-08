package com.coolsee.live.comm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class XSharedParamManager
{
	private static final String TAG = "XSharedParamManager";
	private final static String DLNA_PARAM_DB = "cool_see_db";

	public final static String TOKEN = "TOKEN";
	public final static String OTHERNAME = "OTHERNAME";
	public final static String UID = "UID";

	SharedPreferences mPreferences = null;
	Context mContext = null;

	public XSharedParamManager(Context context)
	{
		mContext = context;
		if( mContext != null )
		{
			mPreferences = context
					.getSharedPreferences(DLNA_PARAM_DB, Context.MODE_PRIVATE);
		}
	}

	public void setUid(String uid)
	{
		if( mPreferences != null )
		{
			Editor editor = mPreferences.edit();

			editor.putString(UID, uid);

			editor.commit();
		}
	}

	public String getUid()
	{
		if( mPreferences != null )
		{
			String uid = mPreferences.getString(UID, "");

			return uid;
		}

		return null;
	}

	public void setToken(String token)
	{
		if( mPreferences != null )
		{
			Editor editor = mPreferences.edit();

			editor.putString(TOKEN, token);

			editor.commit();
		}
	}

	public String getToken()
	{
		if( mPreferences != null )
		{
			String stbToken = mPreferences.getString(TOKEN, "");

			return stbToken;
		}

		return null;
	}

	public void setOtherName(String otherName)
	{
		if( mPreferences != null )
		{
			Editor editor = mPreferences.edit();

			editor.putString(OTHERNAME, otherName);

			editor.commit();
		}
	}

	public String getOtherName()
	{
		if( mPreferences != null )
		{
			String otherName = mPreferences.getString(OTHERNAME, "");

			return otherName;
		}

		return null;
	}

	public void setUserValue(String key, String value)
	{
		if( mPreferences != null )
		{
			Editor editor = mPreferences.edit();

			editor.putString(key, value);

			editor.commit();
		}
	}

	public String getUserValue(String key)
	{
		if( mPreferences != null )
		{
			String value = mPreferences.getString(key, "");

			return value;
		}

		return null;
	}

	public void setUserIntValue(String key, int value)
	{
		if( mPreferences != null )
		{
			Editor editor = mPreferences.edit();
			editor.putInt(key, value);
			editor.commit();
		}
		return;
	}

	public long getUserLongValue(String key, long defValue)
	{
		if( mPreferences != null )
		{
			long value = mPreferences.getLong(key, defValue);
			return value;
		}
		return defValue;
	}

	public void setUserLongValue(String key, long value)
	{
		if( mPreferences != null )
		{
			Editor editor = mPreferences.edit();
			editor.putLong(key, value);
			editor.commit();
		}
		return;
	}

	public int getUserIntValue(String key, int defData)
	{
		if( mPreferences != null )
		{
			int value = mPreferences.getInt(key, defData);
			return value;
		}
		return defData;
	}

	public void setUserBooleanValue(String key, boolean value)
	{
		if( mPreferences != null )
		{
			Editor editor = mPreferences.edit();
			editor.putBoolean(key, value);
			editor.commit();
		}
		return;
	}

	public boolean getUserBooleanValue(String key, boolean defData)
	{
		if( mPreferences != null )
		{
			boolean value = mPreferences.getBoolean(key, defData);
			return value;
		}
		return false;
	}
}