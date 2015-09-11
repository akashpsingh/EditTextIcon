package com.edittexticon.android.edittexticon;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class EditTextIcon extends LinearLayout {

    private static final String TAG = EditTextIcon.class.toString();
    private static final float ICON_WEIGHT = 0.1f;
    private static final float EDIT_TEXT_WEIGHT = 0.9f;

    private ImageView mImageView;

    private EditText mEditText;

    private int mIconResource;

    private String mHint;

    private int mUnderlineColor;

    private float mSpacing;

    private boolean mHideUnderline;

    private boolean mIsPassword;

    public EditTextIcon(Context context) {
        this(context, null);
    }

    public EditTextIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttributes(context, attrs);
        initialize();
    }

    public EditTextIcon(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void parseAttributes(Context context, AttributeSet attributeSet) {

        if(attributeSet == null) return;

        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.EditTextIcon);

        try {

            mIconResource = a.getResourceId(R.styleable.EditTextIcon_iconSrc, 0);
            mHint = a.getString(R.styleable.EditTextIcon_hint);
            mUnderlineColor = a.getColor(R.styleable.EditTextIcon_underlineColor, 0);
            mHideUnderline = a.getBoolean(R.styleable.EditTextIcon_hideUnderline, false);
            mIsPassword = a.getBoolean(R.styleable.EditTextIcon_isPassword, false);
            mSpacing = a.getDimension(R.styleable.EditTextIcon_spacing, 0);

        } catch (Exception e) {

            Log.d(TAG, "Not able to parse attributes");

        } finally {

            a.recycle();

        }
    }

    private void initialize() {

        setOrientation(HORIZONTAL);

        //add ImageView to the view
        mImageView = new ImageView(getContext());

        mImageView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, ICON_WEIGHT));

        if(mIconResource != 0) {

            mImageView.setImageResource(mIconResource);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        }

        addView(mImageView);

        //add EditText to the view

        mEditText = new EditText(getContext());

        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, EDIT_TEXT_WEIGHT);
        mEditText.setLayoutParams(lp);

        //set the hint
        if(mHint != null) {
            mEditText.setHint(mHint);
        }

        //set the underline color
        if(mUnderlineColor != 0) {
            mEditText.getBackground().setColorFilter(mUnderlineColor, PorterDuff.Mode.SRC_IN);
        }

        //hide the underline of the edittext
        if(mHideUnderline) {
            mEditText.setBackgroundColor(Color.TRANSPARENT);
        }

        mEditText.setInputType(mIsPassword ? InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT : InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);

        addView(mEditText);

    }

    public EditText getEditText() {
        return mEditText;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public Editable getText() {
        return mEditText.getText();
    }
}
