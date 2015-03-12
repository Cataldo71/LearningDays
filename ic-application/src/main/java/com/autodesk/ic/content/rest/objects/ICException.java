package com.autodesk.ic.content.rest.objects;

/**
 * Created by cataldp on 1/27/2015.
 */
public class ICException extends RuntimeException{
    protected String errorCode;

    /**
     * Used when you want to overwrite the default category on the error code.
     */
    protected String errorCategory;

    /**
     * Meta data.  Currently for internal use only.
     */
    protected Object[] metadata;

    public ICException(String errorCode) {
        super();
        this.errorCode = errorCode;
        this.errorCategory = errorCategory;
    }

    public ICException(String errorCode, Object... metadata) {
        super();
        this.errorCode = errorCode;
        this.errorCategory = errorCategory;
        this.metadata = metadata;
    }

    public ICException(String errorCode, Throwable innerException) {
        super(innerException);
        this.errorCode = errorCode;
        this.errorCategory = errorCategory;
    }

    public ICException(String errorCode, Throwable innerException, Object... metadata) {
        super(innerException);
        this.errorCode = errorCode;
        this.errorCategory = errorCategory;
        this.metadata = metadata;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getMetadata() {
        return metadata;
    }

    public String getErrorCategory() {
        return errorCategory;
    }

    public void setErrorCategory(String errorCategory) {
        this.errorCategory = errorCategory;
    }

    public void setMetadata(Object[] metadata) {
        this.metadata = metadata;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        if (metadata != null) {
            for (Object o : metadata) {
                if (o != null)
                    result = 31 * result + o.hashCode();
            }
        }

        result = 31 * result + errorCode.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !ICException.class.isAssignableFrom(obj.getClass()))
            return false;

        ICException ex = (ICException)obj;

        if (errorCode != ex.getErrorCode())
            return false;

        if (metadata != null) {
            if (ex.getMetadata() == null || metadata.length != ex.getMetadata().length)
                return false;

            for (int i=0; i<metadata.length; i++) {
                if (metadata[i] == null && ex.getMetadata()[i] != null)
                    return false;
                else if (metadata[i] != null && !metadata[i].equals(ex.getMetadata()[i]))
                    return false;
            }
        }
        else if (ex.getMetadata() != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        String result = errorCode;
        return result;
    }
}
