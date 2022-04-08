/*
 * 文件名称:          SearchBar.java
 *  
 * 编译器:            android2.2
 * 时间:              下午4:46:54
 */
package com.example.alldocument.library.officereader;

import com.example.alldocument.library.constant.EventConstant;
import com.example.alldocument.library.officereader.beans.AImageButton;
import com.example.alldocument.library.officereader.beans.AImageFindButton;
import com.example.alldocument.library.officereader.beans.AToolsbar;
import com.example.alldocument.library.system.IControl;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2012-3-9
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:           
 * <p>
 * <p>
 */
public class FindToolBar extends AToolsbar
{    
    /**
     * 
     * @param context
     * @param spreadSheet
     */
    public FindToolBar(Context content, IControl control)
    {
        super(content, control);        
        init();
    }
    
    /**
     * 
     */
    private void init()
    {
        // left button
        AImageButton sb = addButton(com.example.alldocument.R.drawable.file_left, com.example.alldocument.R.drawable.file_left_disable,
            com.example.alldocument.R.string.app_searchbar_backward, EventConstant.APP_FIND_BACKWARD, false);
        sb.getLayoutParams().width = buttonWidth / 2;
        sb.setEnabled(false);

        //right button
        sb = addButton(com.example.alldocument.R.drawable.file_right, com.example.alldocument.R.drawable.file_right_disable,
            com.example.alldocument.R.string.app_searchbar_forward, EventConstant.APP_FIND_FORWARD, false);
        sb.getLayoutParams().width = buttonWidth / 2;
        sb.setEnabled(false);
        
        
        //search
        Resources res = getContext().getResources();
        searchButton = new AImageFindButton(
            getContext(), control, res.getString(com.example.alldocument.R.string.app_searchbar_find),
            com.example.alldocument.R.drawable.file_search, com.example.alldocument.R.drawable.file_search_disable, EventConstant.APP_FINDING,
            getResources().getDisplayMetrics().widthPixels - buttonWidth * 3 / 2, buttonWidth / 2, buttonHeight,
            new TextWatcher() 
            {
                
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            { 
                
            } 
              
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            { 
                
            } 
              
            @Override
            public void afterTextChanged(Editable s) 
            { 
                setEnabled(EventConstant.APP_FIND_BACKWARD, false);
                setEnabled(EventConstant.APP_FIND_FORWARD, false);
                searchButton.setFindBtnState(s.length() > 0);                
            } 
        });
        actionButtonIndex.put(EventConstant.APP_FINDING, toolsbarFrame.getChildCount());
        
        toolsbarFrame.addView(searchButton);
    }
    
    /**
     * 
     *
     */
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        searchButton.resetEditTextWidth(getResources().getDisplayMetrics().widthPixels - buttonWidth * 3 / 2);
    }
    
    /**
     * Set the enabled state of this view.
     *
     * @param visibility One of {@link #VISIBLE}, {@link #INVISIBLE}, or {@link #GONE}.
     * @attr ref android.R.styleable#View_visibility
     */
    public void setVisibility(int visibility) 
    {
        super.setVisibility(visibility);
        if(visibility == View.VISIBLE)
        {
            InputMethodManager imm =
                (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
            
            setEnabled(EventConstant.APP_FIND_BACKWARD, false);
            setEnabled(EventConstant.APP_FIND_FORWARD, false);
            searchButton.reset();            
        }
    }
    
    /**
     * 
     */
    public void dispose()
    {
        super.dispose();
        searchButton = null;        
    }
    
    private AImageFindButton searchButton;
}
