/*
 * 文件名称:          AllocMem.java
 *  
 * 编译器:            android2.2
 * 时间:              下午5:16:04
 */
package com.example.alldocument.library.objectpool;

import com.example.alldocument.library.objectpool.IMemObj;

import java.util.Vector;

/**
 * 共享对象管理
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-8-21
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class AllocPool
{

    /**
     * 
     */
    public AllocPool(com.example.alldocument.library.objectpool.IMemObj prototype, int capacity, int capacityIncrement)
    { 
        this.prototype = prototype;
        free = new Vector<com.example.alldocument.library.objectpool.IMemObj>(capacity, capacityIncrement);
    }
    
    /**
     * 
     */
    public synchronized com.example.alldocument.library.objectpool.IMemObj allocObject()
    {
        if (free.size() > 0)
        {
            com.example.alldocument.library.objectpool.IMemObj obj = free.remove(0);
            return obj;
        }
        com.example.alldocument.library.objectpool.IMemObj obj = prototype.getCopy();
        return obj;
    }
    
    /**
     * 
     */
    public synchronized void free(com.example.alldocument.library.objectpool.IMemObj obj)
    {
        free.add(obj);
    }
    
    /**
     * 
     */
    public synchronized void dispose()
    {
        if (free != null)
        {
            free.clear();
        }
    }
    
    private com.example.alldocument.library.objectpool.IMemObj prototype;
    //
    private Vector<IMemObj> free;
}
