package com.mobilesolutionworks.android.validate;

import java.util.LinkedHashSet;

/**
 * Created by yunarta on 25/6/14.
 */
public class Validation
{
    public String name;

    public String property;

    public LinkedHashSet<ValidationRule> rules;

    public Validation()
    {
        rules = new LinkedHashSet<ValidationRule>();
    }

    public void add(ValidationRule rule)
    {
        rules.add(rule);
    }
}
