package com.mobilesolutionworks.android.validate;

import android.os.Bundle;

/**
 * Created by yunarta on 25/6/14.
 */
public interface ValidationRuleFactory
{
    ValidationRule create(String name, Bundle bundle);
}
