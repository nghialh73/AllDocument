package com.example.alldocument.library.common.picture;

import com.example.alldocument.library.common.picture.PictureConverterMgr;

public class PictureConverterThread extends Thread
{
	public  PictureConverterThread(com.example.alldocument.library.common.picture.PictureConverterMgr converterMgr, String srcPath, String dstPath, String type )
    {
        this.converterMgr = converterMgr;
        this.type = type;
        
        this.sourPath = srcPath;
        this.destPath = dstPath;
    }
    
    public void run() 
    {
    	converterMgr.convertPNG(sourPath, destPath, type,  false);
    }   
    
    
    private PictureConverterMgr converterMgr;
    private String type;
    private String sourPath;
    private String destPath;
}
