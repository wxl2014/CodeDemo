package com.coolsee.live.mvp.base;

import android.app.Activity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.coolsee.live.bean.Channel;
import com.coolsee.live.bean.ChannelGroup;
import com.coolsee.live.comm.CommApi;
import com.coolsee.live.comm.Constant;
import com.coolsee.live.mvp.MainActivityContract;
import com.coolsee.live.ui.MyApplication;
import com.coolsee.live.volley.MyStringRequest;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class MainActivityPresenter implements MainActivityContract.Presenter
{
	private Activity mContext;
	private MainActivityContract.View mView;
	private RequestQueue mDownQueue;

	public MainActivityPresenter(Activity activity,
			MainActivityContract.View view, RequestQueue downQueue)
	{
		view.setPresenter(this);
		mDownQueue = downQueue;
		mContext = activity;
		mView = view;
	}

	@Override
	public void start()
	{

	}

	@Override
	public void getChannels()
	{
		String url = Constant.GET_CHANNELS;
		MyStringRequest request = new MyStringRequest(url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String s)
			{
                try {
                    byte[] bytes = CommApi.decode(s);
                    String json = new String(bytes);
                    KLog.e(json);
                    Gson gg = new Gson();
                    List<ChannelGroup> channelGroupList = CommApi.getChannelGroups();
                    channelGroupList.clear();
                    JSONArray channelGroups = new JSONArray(json);
                    for( int i =0;i<channelGroups.length();i++ )
                    {
                        JSONObject channelGroupTemp = channelGroups.getJSONObject(i);
                        String id = channelGroupTemp.getString("id");
                        String name = channelGroupTemp.getString("name");

//                        JSONArray channelListJsonArray = channelGroupTemp.getJSONArray("channel");
//                        ChannelGroup cg = new ChannelGroup();
//                        cg.setId(id);
//                        cg.setName(name);
//                        for( int channelPs=0;channelPs<channelListJsonArray.length();channelPs++)
//                        {
//                            JSONObject channelTempJson = channelListJsonArray.getJSONObject(channelPs);
//                            String strChannelObj = channelTempJson.toString();
//                            Channel channelObj = gg.fromJson(strChannelObj, Channel.class);
//                            cg.getChannels().add(channelObj);
//                        }
                        JSONObject channelListJsonObj = (JSONObject) channelGroupTemp.get("channel");
                        ChannelGroup cg = new ChannelGroup();
                        cg.setId(id);
                        cg.setName(name);
                        Iterator<String> keys = channelListJsonObj.keys();
                        while (keys.hasNext())
                        {
                            String keyTemp = keys.next();
                            JSONObject channelTempJson = channelListJsonObj.getJSONObject(keyTemp);
                            String strChannelObj = channelTempJson.toString();
                            Channel channelObj = gg.fromJson(strChannelObj, Channel.class);
                            cg.getChannels().add(channelObj);
                        }
                        channelGroupList.add(cg);
                    }
                    KLog.e("size:"+channelGroupList.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
			}
		}, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError volleyError)
			{

			}
		});
		mDownQueue.add(request);
	}
}
