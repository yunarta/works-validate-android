package com.mobilesolutionworks.android.validate;

import android.os.Bundle;
import android.text.TextUtils;

/**
 * Created by yunarta on 25/6/14.
 */
public class NotEmpty extends ValidationRule {

    @Override
    public void onCreate(Bundle bundle) {

    }

    @Override
    public boolean onValidate(Object value) {
        if (value instanceof String) {
            return !TextUtils.isEmpty((String) value);
        } else if (value instanceof Integer) {
            return ((Integer) value).intValue() > 0;
        } else {
            return value != null;
        }
    }
}
