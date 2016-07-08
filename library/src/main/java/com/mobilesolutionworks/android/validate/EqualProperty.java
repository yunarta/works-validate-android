package com.mobilesolutionworks.android.validate;

import android.os.Bundle;

/**
 * Created by yunarta on 8/7/16.
 */

public class EqualProperty extends ValidationRule {

    public String equalTo;

    @Override
    public void onCreate(Bundle bundle)
    {
    }

    @Override
    public boolean validate(Bundle bundle, String property) {
        Object first = bundle.get(property);
        Object second = bundle.get(equalTo);

        if (first == null || second == null) return true;
        return second.equals(first);
    }

    @Override
    public boolean onValidate(Object value)
    {
        return true;
    }
}
