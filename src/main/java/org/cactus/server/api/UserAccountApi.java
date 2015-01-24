package org.cactus.server.api;

public class UserAccountApi extends AbstractApi {

    public static final String USER = "/user";

    public static final String ID = "/{id}";

    public static class URL {
        public static String GET_BY_ID = build(USER, ID);
    }

}
