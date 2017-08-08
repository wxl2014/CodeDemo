package com.coolsee.live.ui;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.coolsee.live.bean.ChannelGroup;
import com.coolsee.live.comm.CommApi;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application
{
	private final String TAG = "MyApplication";
	private Bus mBus = new Bus();
	private boolean testVersion;
    private List<ChannelGroup> mChannelGroups = new ArrayList<>();

	@Override
	public void onCreate()
	{
		super.onCreate();
		getVersionType();
	}

    /**
     * 从配置文件中获取当前版本是release还是debug
     */
	private void getVersionType()
	{
		try
		{
			ApplicationInfo appInfo = getPackageManager()
					.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);

			testVersion = appInfo.metaData.getBoolean("VERSION_TYPE");
		} catch( PackageManager.NameNotFoundException e )
		{
			e.printStackTrace();
		}
		return;
	}

	public Bus getBus()
	{
		return mBus;
	}

	public boolean isTestVersion()
	{
		return testVersion;
	}

	public void setTestVersion(boolean testVersion)
	{
		this.testVersion = testVersion;
	}

    public List<ChannelGroup> getChannelGroups()
    {
        return mChannelGroups;
    }
}