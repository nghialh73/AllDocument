/*
 * 文件名称:          TokenManage.java
 *  
 * 编译器:            android2.2
 * 时间:              下午2:31:08
 */
package com.example.alldocument.library.objectpool;

import com.example.alldocument.library.objectpool.IMemObj;
import com.example.alldocument.library.objectpool.ParaToken;

import java.util.Vector;

/**
 * token manage
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-9-3
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class TokenManage
{
    // token size
    private static final int TOKEN_SIZE = 10;
    //
    public static TokenManage kit = new TokenManage();
    
    /**
     * 
     */
    public static TokenManage instance()
    {
        return kit;
    }
    
    /**
     * 
     */
    public synchronized com.example.alldocument.library.objectpool.ParaToken allocToken(IMemObj obj)
    {
        com.example.alldocument.library.objectpool.ParaToken token = null;
        if (paraTokens.size() >= TOKEN_SIZE)
        {
            for (int i = 0; i < TOKEN_SIZE; i++)
            {
                if (paraTokens.get(i).isFree())
                {
                    token = paraTokens.remove(i);
                    break;
                }
            }
            //token.free();
            paraTokens.add(token);
        }
        else
        {
            token = new com.example.alldocument.library.objectpool.ParaToken(obj);
            paraTokens.add(token);
            
        }
        return token;
    }
    
    //
    public Vector<com.example.alldocument.library.objectpool.ParaToken> paraTokens = new Vector<ParaToken>(TOKEN_SIZE);
}
