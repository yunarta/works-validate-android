package com.mobilesolutionworks.android.validate;

import java.util.LinkedHashSet;

/**
 * Created by yunarta on 25/6/14.
 */
public class Validation
{
    public String property;

    public LinkedHashSet<ValidationRule> rules;

    public Validation()
    {
        rules = new LinkedHashSet<>();
    }

    public void add(ValidationRule rule)
    {
        rules.add(rule);
    }
}
