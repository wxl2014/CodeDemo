package com.coolsee.live.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.coolsee.live.comm.XSharedParamManager;
import com.squareup.otto.Bus;

public abstract class BaseActivity extends AppCompatActivity
{
	protected RequestQueue mDownloadQueue = null;
	protected Bus mBus = null;
	protected XSharedParamManager mShare = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		getWindow()
				.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		if( null == mDownloadQueue )
		{
			mDownloadQueue = Volley.newRequestQueue(BaseActivity.this);
		}
		MyApplication mapp = (MyApplication) getApplication();
		if( null != mapp && null == mBus )
		{
			mBus = mapp.getBus();
			mBus.register(this);
		}
		if( null == mShare )
		{
			mShare = new XSharedParamManager(this);
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	public void onBackPressed()
	{
		exit();
	}

	@Override
	protected void onDestroy()
	{
		if( null != mDownloadQueue )
		{
			mDownloadQueue.stop();
			mDownloadQueue = null;
		}
		if( null != mBus )
		{
			mBus.unregister(this);
		}
		if( null != mShare )
		{
			mShare = null;
		}
		super.onDestroy();
	}

	protected void exit()
	{
        finish();
		return;
	}
}
