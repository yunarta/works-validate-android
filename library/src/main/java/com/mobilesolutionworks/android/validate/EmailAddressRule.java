package com.mobilesolutionworks.android.validate;

import android.os.Bundle;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Created by yunarta on 25/9/14.
 */
public class EmailAddressRule extends ValidationRule {

    @Override
    public void onCreate(Bundle bundle) {

    }

    @Override
    public boolean onValidate(Object value) {
        try {
            return EmailValidator.getInstance().isValid(String.valueOf(value));
        } catch (Exception e) {
            return false;
        }
    }
}
