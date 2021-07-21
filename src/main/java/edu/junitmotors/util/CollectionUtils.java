package edu.junitmotors.util;

import java.util.Collection;

public class CollectionUtils {

    public static Boolean isNullOrEmpty(Collection<?> anyCollection) {
        return ( anyCollection == null || anyCollection.isEmpty() );
    }
}
