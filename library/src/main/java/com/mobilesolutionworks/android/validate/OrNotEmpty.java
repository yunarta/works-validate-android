package com.mobilesolutionworks.android.validate;

import android.os.Bundle;
import com.mobilesolutionworks.android.managedview.IObjectProvider;

/**
 * Created by yunarta on 25/6/14.
 */
public class OrNotEmpty extends ValidationRule
{
    public String other;

    @Override
    public void onCreate(Bundle bundle)
    {

    }

    @Override
    public boolean validate(IObjectProvider provider, String name, String property)
    {
        Object first = provider.getProperty(name, property);
        Object second = provider.getProperty(name, other);

        return first != null || second != null;
    }

    @Override
    public boolean onValidate(Object value)
    {
        return true;
    }
}
