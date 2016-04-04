package com.mobilesolutionworks.android.validate;

import android.os.Bundle;
import android.webkit.URLUtil;

/**
 * Created by yunarta on 6/3/16.
 */
public class ValidURLRule extends ValidationRule {

    @Override
    public void onCreate(Bundle bundle) {

    }

    @Override
    public boolean onValidate(Object value) {
        try {
            return URLUtil.isValidUrl(String.valueOf(value));
        } catch (Exception e) {
            return false;
        }
    }
}

