package com.karma.followback.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.karma.followback.R;

import java.util.HashMap;

/**
 * Created by akashsingh on 01/12/15.
 */
public class TextViewPlus extends TextView {
    private static final String TAG = "TextViewPlus";
    private static HashMap<String,Typeface> sTypefaceStack;
    public TextViewPlus(Context context) {
        super(context);
    }

    public TextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        String customFont = a.getString(R.styleable.TextViewPlus_customFont);
        if(customFont != null)
        {
            setCustomFont(ctx, customFont);
        }
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
            if(sTypefaceStack == null) {

                sTypefaceStack=new HashMap<String,Typeface>();
                tf = Typeface.createFromAsset(ctx.getAssets(), asset);
                sTypefaceStack.put(asset,tf);
            }
            else if(sTypefaceStack.containsKey(asset) && sTypefaceStack.get(asset) != null) {

                tf=sTypefaceStack.get(asset);
            }
            else {
                tf = Typeface.createFromAsset(ctx.getAssets(), asset);
                sTypefaceStack.put(asset, tf);
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: " + e.getMessage());
            return false;
        }

        setTypeface(tf);
        return true;
    }

}
