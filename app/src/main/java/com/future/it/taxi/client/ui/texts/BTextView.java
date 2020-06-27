package com.future.it.taxi.client.ui.texts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.future.it.taxi.client.ui.fonts.FontCache;
import com.future.it.taxi.client.ui.fonts.FontsManager;


/**
 * Created by Tariqul.Islam on 5/17/17.
 */

public class BTextView extends TextView {

    public BTextView(Context context) {
            super(context);
        }

    public BTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public BTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(FontsManager.ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        return FontCache.getTypeface(FontsManager.fontBold, context);
    }

}
