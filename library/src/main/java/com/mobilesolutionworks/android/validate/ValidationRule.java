package com.mobilesolutionworks.android.validate;

import android.os.Bundle;
import com.mobilesolutionworks.android.managedview.IObjectProvider;

/**
 * Created by yunarta on 25/6/14.
 */
public abstract class ValidationRule
{
    public String text;

    public void onCreate(Bundle bundle)
    {

    }

    public boolean validate(IObjectProvider provider, String name, String property)
    {
        Object value = provider.getProperty(name, property);
        return onValidate(value);
    }

    public abstract boolean onValidate(Object value);
}
