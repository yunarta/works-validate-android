package com.mobilesolutionworks.android.validate;

import android.os.Bundle;

/**
 * Created by yunarta on 25/6/14.
 */
public class EqualRule extends ValidationRule
{
    public String equal;

    @Override
    public void onCreate(Bundle bundle)
    {

    }

    @Override
    public boolean onValidate(Object value)
    {
        return equal.equals(value);
    }
}
