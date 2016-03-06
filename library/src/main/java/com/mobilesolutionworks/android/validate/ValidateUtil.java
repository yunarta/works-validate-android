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
        EMAIL_ADDRESS("email-address"),
        VALID_URL("valid-url"),
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
            mValidations = new LinkedHashSet<Validation>();

            XmlResourceParser parser = context.getResources().getXml(xml);
            parser.next();

            int type;
            type = parser.getEventType();
            Validation validation = null;
            ValidationRule rule = null;

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
                        } else if (ValidationTags.RULES.is(name) && factory != null) {
                            int id = parser.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "text", -1);
                            String text = "";

                            if (id != -1) {
                                text = context.getString(id);
                            }

                            Bundle bundle = new Bundle();

                            int attributeCount = parser.getAttributeCount();
                            for (int i = 0; i < attributeCount; i++) {
                                if (parser.getAttributeNamespace(i) == null) {
                                    String attName = parser.getAttributeName(i);
                                    String attValue = parser.getAttributeValue(i);

                                    bundle.putString(attName, attValue);
                                }
                            }

                            bundle.putString("text", text);

                            String className = parser.getAttributeValue(null, "class");
                            ValidationRule notEmpty = factory.create(className, bundle);
                            notEmpty.onCreate(bundle);

                            if (!TextUtils.isEmpty(text)) {
                                notEmpty.text = text;
                            }

                            if (validation != null) {
                                validation.add(notEmpty);
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
