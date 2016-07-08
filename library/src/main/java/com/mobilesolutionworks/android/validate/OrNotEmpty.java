package com.mobilesolutionworks.android.validate;

import android.os.Bundle;

/**
 * Created by yunarta on 25/6/14.
 */
public class OrNotEmpty extends ValidationRule {
    public String other;

    @Override
    public void onCreate(Bundle bundle) {

    }

    @Override
    public boolean validate(Bundle bundle, String property) {
        Object first = bundle.get(property);
        Object second = bundle.get(other);

        return (first == null || second == null) || first.equals(other);
    }

    @Override
    public boolean onValidate(Object value) {
        return true;
    }
}
