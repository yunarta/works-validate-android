package com.mobilesolutionworks.android.validate;

import android.os.Bundle;

/**
 * Created by yunarta on 25/6/14.
 */
public abstract class ValidationRule {

    public String text;

    public void onCreate(Bundle bundle) {

    }

    public boolean validate(Bundle bundle, String property) {
        return onValidate(bundle.get(property));
    }

    public abstract boolean onValidate(Object value);
}
