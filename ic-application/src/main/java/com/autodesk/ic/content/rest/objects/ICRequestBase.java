package com.autodesk.ic.content.rest.objects;

/**
 * Created by cataldp on 1/27/2015.
 */
public class ICRequestBase {

    protected void validate() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ICRequestBase that = (ICRequestBase) o;

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

}
