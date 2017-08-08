package com.coolsee.live.bean;

import java.util.List;

public class Channel
{
	private String id;
	private String url;
	private String name;
	private String icon;
	private String cid;
	private String mid;
	private String px;
	private List<SourceBean> source;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getCid()
	{
		return cid;
	}

	public void setCid(String cid)
	{
		this.cid = cid;
	}

	public String getMid()
	{
		return mid;
	}

	public void setMid(String mid)
	{
		this.mid = mid;
	}

	public String getPx()
	{
		return px;
	}

	public void setPx(String px)
	{
		this.px = px;
	}

	public List<SourceBean> getSource()
	{
		return source;
	}

	public void setSource(List<SourceBean> source)
	{
		this.source = source;
	}

	public static class SourceBean
	{
		private String url;

		public String getUrl()
		{
			return url;
		}

		public void setUrl(String url)
		{
			this.url = url;
		}
	}
}
