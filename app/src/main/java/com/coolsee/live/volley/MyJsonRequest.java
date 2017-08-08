package com.coolsee.live.volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

public class MyJsonRequest extends JsonObjectRequest
{
	private final String TAG = "MyStringRequest";
	private boolean mNeedToken = false;// 是否需要在http头里面添加token
	private Context mCtx = null;

	public MyJsonRequest(Context ctx, int method, String url, JSONObject jsObj,
			Response.Listener<JSONObject> listener,
			Response.ErrorListener errorListener)
	{
		super(method, url, jsObj, listener, errorListener);
		if( null != ctx )
		{
			mCtx = ctx;
			mNeedToken = true;
		}
	}

	/**
	 * @param ctx
	 *            传入上下文，http请求头中添加token值
	 */
	public MyJsonRequest(Context ctx, String url, JSONObject jsObj,
			Response.Listener<JSONObject> listener,
			Response.ErrorListener errorListener)
	{
		super(url, jsObj, listener, errorListener);
		if( null != ctx )
		{
			mCtx = ctx;
			mNeedToken = true;
		}
	}

	public MyJsonRequest(String url, int method, JSONObject jsObj,
			Response.Listener<JSONObject> listener,
			Response.ErrorListener errorListener)
	{
		super(method, url, jsObj, listener, errorListener);
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(
			NetworkResponse response)
	{
		try
		{
			String resString = new String(response.data, "UTF-8");
			JSONObject jsobj = new JSONObject(resString);
			return Response.success(jsobj, HttpHeaderParser
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
		headers.put("Accept", "application/json");
		// headers.put("Content-Type","application/x-json");
		// headers.put("Content-Type","text/json");
		// headers.put("Content-Type","text/plain");
		// headers.put("Content-Type","text/html");
		return headers;
	}
}
