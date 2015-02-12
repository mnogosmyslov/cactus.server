package org.cactus.server.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * This Root API class store Base URL
 */
@PropertySource("classpath:messages/url.properties")
public abstract class AbstractApi {

    @Resource
    private static Environment env;

    //TODO remote server
    private final static String contextURL = env.getRequiredProperty("module.server.url");

    protected static String build(String... params){
        StringBuilder builder = new StringBuilder(contextURL);
        builder.append(StringUtils.join(params));

        return builder.toString();
    }

    public static String getContextURL() {
        return contextURL;
    }

}
