package org.cactus.server.api;

import org.apache.commons.lang3.StringUtils;

/**
 * This Root API class store Base URL
 */
public abstract class AbstractApi {

    //TODO remote server
    private final static String contextURL = "http://localhost:8080/server";

    protected static String build(String... params){
        StringBuilder builder = new StringBuilder(contextURL);
        builder.append(StringUtils.join(params));

        return builder.toString();
    }

    public static String getContextURL() {
        return contextURL;
    }

}
