package com.mobilesolutionworks.android.validate;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.LinkedHashSet;

/**
 * Created by yunarta on 25/6/14.
 */
public class ValidateUtil {

    enum ValidationTags {
        VALIDATE("validate"),
        NOT_EMPTY("not-empty"),
        OR_NOT_EMPTY("or-not-empty"),
        EQUAL("equal"),
        EQUAL_PROPERTY("equal-property"),
        EMAIL_ADDRESS("email-address"),
        VALID_URL("valid-url"),
        PATTERN("pattern"),
        RULES("rules"),;

        private String mTag;

        ValidationTags(String tag) {
            mTag = tag;
        }

        public String tag() {
            return mTag;
        }

        public boolean is(String name) {
            return mTag.equals(name);
        }
    }

    public LinkedHashSet<Validation> mValidations;

    public String mLastError;

    public ValidateUtil(Context context, int xml) {
        this(context, xml, null, false);
    }

    public ValidateUtil(Context context, int xml, ValidationRuleFactory factory) {
        this(context, xml, factory, false);
    }

    public ValidateUtil(Context context, int xml, ValidationRuleFactory factory, boolean warn) {
        try {
            mValidations = new LinkedHashSet<>();

            XmlResourceParser parser = context.getResources().getXml(xml);
            parser.next();

            int type;
            type = parser.getEventType();
            Validation validation = null;

            while (type != XmlResourceParser.END_DOCUMENT) {
                switch (type) {
                    case XmlResourceParser.START_TAG: {
                        String name = parser.getName();

                        if (ValidationTags.VALIDATE.is(name)) {
                            validation = new Validation();
                            validation.property = parser.getAttributeValue(null, "property");

                        } else if (ValidationTags.NOT_EMPTY.is(name)) {
                            NotEmpty validator = new NotEmpty();
                            int text = parser.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "text", -1);
                            if (text != -1) {
                                validator.text = context.getString(text);
                            }

                            if (validation != null) {
                                validation.add(validator);
                            }
                        } else if (ValidationTags.OR_NOT_EMPTY.is(name)) {
                            OrNotEmpty validator = new OrNotEmpty();
                            validator.other = parser.getAttributeValue(null, "property");

                            int text = parser.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "text", -1);
                            if (text != -1) {
                                validator.text = context.getString(text);
                            }

                            if (validation != null) {
                                validation.add(validator);
                            }
                        } else if (ValidationTags.EQUAL.is(name)) {
                            EqualRule validator = new EqualRule();
                            validator.equal = parser.getAttributeValue(null, "value");

                            int text = parser.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "text", -1);
                            if (text != -1) {
                                validator.text = context.getString(text);
                            }

                            if (validation != null) {
                                validation.add(validator);
                            }
                        } else if (ValidationTags.PATTERN.is(name)) {
                            PatternRule validator = new PatternRule();
                            validator.pattern = parser.getAttributeValue(null, "matches");

                            int text = parser.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "text", -1);
                            if (text != -1) {
                                validator.text = context.getString(text);
                            }

                            if (validation != null) {
                                validation.add(validator);
                            }
                        } else if (ValidationTags.EMAIL_ADDRESS.is(name)) {
                            EmailAddressRule validator = new EmailAddressRule();

                            int text = parser.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "text", -1);
                            if (text != -1) {
                                validator.text = context.getString(text);
                            }

                            if (validation != null) {
                                validation.add(validator);
                            }
                        } else if (ValidationTags.VALID_URL.is(name)) {
                            ValidURLRule validator = new ValidURLRule();

                            int text = parser.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "text", -1);
                            if (text != -1) {
                                validator.text = context.getString(text);
                            }

                            if (validation != null) {
                                validation.add(validator);
                            }
                        } else if (ValidationTags.EQUAL_PROPERTY.is(name)) {
                            EqualProperty validator = new EqualProperty();

                            validator.equalTo = parser.getAttributeValue(null, "property");

                            int text = parser.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "text", -1);
                            if (text != -1) {
                                validator.text = context.getString(text);
                            }

                            if (validation != null) {
                                validation.add(validator);
                            }
                        }
                        break;
                    }

                    case XmlResourceParser.END_TAG: {
                        String name = parser.getName();
                        if ("validate".equals(name)) {
                            mValidations.add(validation);
                        }
                    }
                }

                type = parser.next();
            }
        } catch (Exception e) {
            if (warn) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public boolean validate(Bundle bundle) {
        mLastError = null;
        for (Validation validation : mValidations) {
            for (ValidationRule rule : validation.rules) {
                if (!rule.validate(bundle, validation.property)) {
                    mLastError = rule.text;
                    return false;
                }
            }
        }

        return true;
    }

    public boolean hasError() {
        return mLastError != null;
    }

    public String getLastError() {
        return mLastError;
    }
}
