package com.coolsee.live.bean;

import java.util.ArrayList;
import java.util.List;

public class ChannelGroup
{
	private String id;
	private String name;
	List<Channel> channels = new ArrayList<>();

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Channel> getChannels()
	{
		return channels;
	}
}
