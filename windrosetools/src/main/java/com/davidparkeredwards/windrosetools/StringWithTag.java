package com.davidparkeredwards.windrosetools;

/**
 * Created by davidedwards on 5/30/17.
 */

public class StringWithTag {

    public String string;
    public Object tag;

    public StringWithTag(String stringPart, Object tagPart) {
        string = stringPart;
        tag = tagPart;
    }

    @Override
    public String toString() {
        return string;
    }
}
