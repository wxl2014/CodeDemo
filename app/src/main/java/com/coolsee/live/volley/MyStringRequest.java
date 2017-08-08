package com.coolsee.live.volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;


public class MyStringRequest extends StringRequest
{
	private final String TAG = "MyStringRequest";
	private Context mCtx = null;

	public MyStringRequest(Context ctx, int method, String url,
                           Response.Listener<String> listener,
                           Response.ErrorListener errorListener)
	{
		super(method, url, listener, errorListener);
		if( null != ctx )
		{
			mCtx = ctx;
		}
	}

	public MyStringRequest(String token, int method, String url,
                           Response.Listener<String> listener,
                           Response.ErrorListener errorListener)
	{
		super(method, url, listener, errorListener);
	}

	/**
	 * @param ctx
	 *            传入上下文，http请求头中添加token值
	 */
	public MyStringRequest(Context ctx, String url,
                           Response.Listener<String> listener,
                           Response.ErrorListener errorListener)
	{
		super(url, listener, errorListener);
		if( null != ctx )
		{
			mCtx = ctx;
		}
	}

	public MyStringRequest(String url, Response.Listener<String> listener,
                           Response.ErrorListener errorListener)
	{
		super(url, listener, errorListener);
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response)
	{
		try
		{
			String resString = new String(response.data, "UTF-8");
			return Response.success(resString, HttpHeaderParser
					.parseCacheHeaders(response));
		} catch( UnsupportedEncodingException e )
		{
			return Response.error(new ParseError(e));
		} catch( Exception je )
		{
			return Response.error(new ParseError(je));
		}
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError
	{
		Map<String, String> headers = new HashMap<String, String>();

//		if( Constant.LAN_ZH.equals(CommonUtil.getLanguage()) )
//		{
//			headers.put("Accept-Language", "zh");
//		}
//		else
//		{
//			headers.put("Accept-Language", "en");
//		}
		return headers;
	}
}
