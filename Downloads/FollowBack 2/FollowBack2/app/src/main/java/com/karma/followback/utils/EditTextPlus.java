package com.karma.followback.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import com.karma.followback.R;

import java.util.HashMap;

/**
 * Created by akashsingh on 01/12/15.
 */
public class EditTextPlus extends EditText {

    private static final String TAG = "EditTextPlus";
    private static HashMap<String,Typeface> sTypefaceStack;
    public EditTextPlus(Context context) {
        super(context);
    }

    public EditTextPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public EditTextPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.EditTextPlus);
        String customFont = a.getString(R.styleable.EditTextPlus_customEditFont);
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
