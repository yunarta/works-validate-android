package com.mobilesolutionworks.android.validate;

import android.os.Bundle;

/**
 * Created by yunarta on 25/6/14.
 */
public class PatternRule extends ValidationRule
{
    public String pattern;

    @Override
    public void onCreate(Bundle bundle)
    {

    }

    @Override
    public boolean onValidate(Object value)
    {
        if (value == null) return false;

        return String.valueOf(value).matches(pattern);
    }
}
