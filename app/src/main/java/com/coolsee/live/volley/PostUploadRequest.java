package com.coolsee.live.volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * Created by J1aDong on 16/8/30.
 */
public class PostUploadRequest extends Request<String>
{
	private Context mContxt;
	/**
	 * 正确数据的时候回掉用
	 */
	private ResponseListener mListener;
	/* 请求 数据通过参数的形式传入 */
	private List<FormImage> mListItem;

	private String BOUNDARY = "WebKitFormBoundaryS4nmHw9nb2Eeusll-"; // 数据分隔线
	private String MULTIPART_FORM_DATA = "multipart/form-data";

	public PostUploadRequest(Context context, String url, List<FormImage> listItem,
							 ResponseListener listener)
	{
		super(Method.POST, url, listener);
		this.mContxt = context;
		this.mListener = listener;
		setShouldCache(false);
		mListItem = listItem;
		// 设置请求的响应事件，因为文件上传需要较长的时间，所以在这里加大了，设为5秒
		setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	}

	/**
	 * 这里开始解析数据
	 * 
	 * @param response
	 *            Response from the network
	 * @return
	 */
	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response)
	{
		try
		{
			String mString = new String(response.data, HttpHeaderParser
					.parseCharset(response.headers));
			return Response.success(mString, HttpHeaderParser
					.parseCacheHeaders(response));
		} catch( UnsupportedEncodingException e )
		{
			return Response.error(new ParseError(e));
		}
	}

	/**
	 * 回调正确的数据
	 * 
	 * @param response
	 *            The parsed response returned by
	 */
	@Override
	protected void deliverResponse(String response)
	{
		mListener.onResponse(response);
	}

	@Override
	public byte[] getBody() throws AuthFailureError
	{
		if( mListItem == null || mListItem.size() == 0 )
		{
			return super.getBody();
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		{
			FormImage desForm = mListItem.get(1);
			StringBuffer desSb = new StringBuffer();
			desSb.append("--" + BOUNDARY);
			desSb.append("\r\n");
			desSb.append("Content-Type: text/plain; charset=UTF-8\r\n");
			desSb.append("Content-Disposition: form-data;");
			desSb.append(" name=\"");
			desSb.append("description");
			desSb.append("\"");
			desSb.append("\r\n");
			desSb.append("Content-Transfer-Encoding: 8bit\r\n");

			desSb.append("\r\n");
			desSb.append(desForm.getDescription()) ;
			desSb.append("\r\n") ;
			try {
				bos.write(desSb.toString().getBytes("utf-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		{
			FormImage siteIdForm = mListItem.get(2);
			StringBuffer siteIdSb = new StringBuffer();
			siteIdSb.append("--" + BOUNDARY);
			siteIdSb.append("\r\n");
			siteIdSb.append("Content-Type: text/plain; charset=UTF-8\r\n");
			siteIdSb.append("Content-Disposition: form-data;");
			siteIdSb.append(" name=\"");
			siteIdSb.append("siteId");
			siteIdSb.append("\"");
			siteIdSb.append("\r\n");
			siteIdSb.append("Content-Transfer-Encoding: 8bit\r\n");
		/* 第三行 */
			siteIdSb.append("\r\n");

			siteIdSb.append(siteIdForm.getSiteId()) ;
			siteIdSb.append("\r\n") ;
			try {
				bos.write(siteIdSb.toString().getBytes("utf-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		{
            FormImage formImage = mListItem.get(0);
			StringBuffer imgsb = new StringBuffer();
			/* 第一行 */
			// `"--" + BOUNDARY + "\r\n"`
			imgsb.append("--" + BOUNDARY);
			imgsb.append("\r\n");
			/* 第二行 */
			// Content-Disposition: form-data; name="参数的名称"; filename="上传的文件名" +
			// "\r\n"
			imgsb.append("Content-Disposition: form-data;");
			imgsb.append(" name=\"");
			imgsb.append(formImage.getName());
			imgsb.append("\"");
			imgsb.append("; filename=\"");
			imgsb.append(formImage.getFileName());
			imgsb.append("\"");
			imgsb.append("\r\n");
			/* 第三行 */
			// Content-Type: 文件的 mime 类型 + "\r\n"
			imgsb.append("Content-Type: ");
			imgsb.append(formImage.getMime());
			imgsb.append("\r\n");
			imgsb.append("Content-Transfer-Encoding: binary\r\n");
			/* 第四行 */
			// "\r\n"
			imgsb.append("\r\n");
			try
			{
				bos.write(imgsb.toString().getBytes("utf-8"));
				/* 第五行 */
				// 文件的二进制数据 + "\r\n"
				bos.write(formImage.getValue());
				bos.write("\r\n".getBytes("utf-8"));
			} catch( IOException e )
			{
				e.printStackTrace();
			}
		}

		/* 结尾行 */
		// `"--" + BOUNDARY + "--" + "\r\n"`
		String endLine = "--" + BOUNDARY + "--" + "\r\n";
		try
		{
			bos.write(endLine.toString().getBytes("utf-8"));
		} catch( IOException e )
		{
			e.printStackTrace();
		}
		Log.v("zgy", "=====formImage====\n" + bos.toString());
		return bos.toByteArray();
	}

	// Content-Type: multipart/form-data; boundary=----------8888888888888
	@Override
	public String getBodyContentType()
	{
		return MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY;
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
