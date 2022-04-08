/*
 * 文件名称:          ResManager.java
 *
 * 编译器:            android2.2
 * 时间:              上午9:39:36
 */
package com.example.alldocument.library.ss.sheetbar;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.alldocument.library.ss.sheetbar.SheetbarResConstant;

import java.io.IOException;

/**
 * TODO: 文件注释
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            jqin
 * <p>
 * 日期:            2012-8-27
 * <p>
 * 负责人:           jqin
 * <p>
 * 负责小组:
 * <p>
 * <p>
 */
public class SheetbarResManager {
    public SheetbarResManager(Context context) {
        this.context = context;

        ClassLoader loader = context.getClassLoader();
        try {
            //sheetbar background
            sheetbarBG = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBAR_BG), null);


            sheetbarLeftShadow = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBAR_SHADOW_LEFT), null);
            sheetbarRightShadow = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBAR_SHADOW_RIGHT), null);
            hSeparator = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBAR_SEPARATOR_H), null);
            normalLeft = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_LEFT), null);
            normalRight = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_RIGHT), null);
            normalMiddle = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_MIDDLE), null);
            pushLeft = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_LEFT), null);
            pushMiddle = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_MIDDLE), null);
            pushRight = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_RIGHT), null);
            focusLeft = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_LEFT), null);
            focusMiddle = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_MIDDLE), null);
            focusRight = Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_RIGHT), null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*sheetbarBG =  Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBAR_BG),
            SheetbarResConstant.RESNAME_SHEETBAR_BG);

        //shadow
        sheetbarLeftShadow= Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBAR_SHADOW_LEFT),
            SheetbarResConstant.RESNAME_SHEETBAR_SHADOW_LEFT);

        sheetbarRightShadow= Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBAR_SHADOW_RIGHT),
            SheetbarResConstant.RESNAME_SHEETBAR_SHADOW_RIGHT);

        //hSeparator
        hSeparator = Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBAR_SEPARATOR_H),
            SheetbarResConstant.RESNAME_SHEETBAR_SEPARATOR_H);

        //normal state
        normalLeft = Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_LEFT),
            SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_LEFT);
        normalRight = Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_RIGHT),
            SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_RIGHT);
        normalMiddle = Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_MIDDLE),
            SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_MIDDLE);

        //push state
        pushLeft = Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_LEFT),
            SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_LEFT);
        pushMiddle = Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_MIDDLE),
            SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_MIDDLE);
        pushRight = Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_RIGHT),
            SheetbarResConstant.RESNAME_SHEETBUTTON_PUSH_RIGHT);

        //focus state
        focusLeft = Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_LEFT),
            SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_LEFT);
        focusMiddle = Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_MIDDLE),
            SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_MIDDLE);
        focusRight = Drawable.createFromResourceStream(context.getResources(), null,
            loader.getResourceAsStream(SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_RIGHT),
            SheetbarResConstant.RESNAME_SHEETBUTTON_FOCUS_RIGHT);
*/
    }

    public Drawable getDrawable(short resID) {
        switch (resID) {
            case SheetbarResConstant.RESID_SHEETBAR_BG:
                return sheetbarBG;

            case SheetbarResConstant.RESID_SHEETBAR_SHADOW_LEFT:
                return sheetbarLeftShadow;

            case SheetbarResConstant.RESID_SHEETBAR_SHADOW_RIGHT:
                return sheetbarRightShadow;

            case SheetbarResConstant.RESID_SHEETBAR_SEPARATOR_H:
                return hSeparator;

            case SheetbarResConstant.RESID_SHEETBUTTON_NORMAL_LEFT:
                return normalLeft;

            case SheetbarResConstant.RESID_SHEETBUTTON_NORMAL_MIDDLE:
                try {
                    return Drawable.createFromStream(context.getAssets().open(SheetbarResConstant.RESNAME_SHEETBUTTON_NORMAL_MIDDLE), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;

            case SheetbarResConstant.RESID_SHEETBUTTON_NORMAL_RIGHT:
                return normalRight;

            case SheetbarResConstant.RESID_SHEETBUTTON_PUSH_LEFT:
                return pushLeft;

            case SheetbarResConstant.RESID_SHEETBUTTON_PUSH_MIDDLE:
                return pushMiddle;

            case SheetbarResConstant.RESID_SHEETBUTTON_PUSH_RIGHT:
                return pushRight;

            case SheetbarResConstant.RESID_SHEETBUTTON_FOCUS_LEFT:
                return focusLeft;

            case SheetbarResConstant.RESID_SHEETBUTTON_FOCUS_MIDDLE:
                return focusMiddle;

            case SheetbarResConstant.RESID_SHEETBUTTON_FOCUS_RIGHT:
                return focusRight;
        }

        return null;
    }

    public void dispose() {
        sheetbarBG = null;

        sheetbarLeftShadow = null;
        sheetbarRightShadow = null;

        hSeparator = null;

        normalLeft = null;
        normalMiddle = null;
        normalRight = null;

        pushLeft = null;
        pushMiddle = null;
        pushRight = null;

        focusLeft = null;
        focusMiddle = null;
        focusRight = null;
    }

    private Context context;
    private Drawable sheetbarBG;

    private Drawable sheetbarLeftShadow, sheetbarRightShadow;

    private Drawable hSeparator;
    //left
    private Drawable normalLeft;
    private Drawable pushLeft;
    private Drawable focusLeft;

    //middle
    private Drawable normalMiddle;
    private Drawable pushMiddle;
    private Drawable focusMiddle;

    //right
    private Drawable normalRight;
    private Drawable pushRight;
    private Drawable focusRight;
}
