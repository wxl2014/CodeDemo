package com.coolsee.live.volley;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;

public class FormImage {

    private String name;

    private String fileName;

    private String siteId;

    private String description;

    private String value;

    private String mime;

    private Bitmap mBitmap;

    public FormImage()
    {}

    public FormImage(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public byte[] getValue() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        return bos.toByteArray();
    }

    public String getMime() {
        return this.mime;
    }

    public void setMime(String mime)
    {
        this.mime = mime;
        return;
    }
}
