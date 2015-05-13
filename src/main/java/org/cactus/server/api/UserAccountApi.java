package org.cactus.server.api;

public class UserAccountApi extends AbstractApi {

    public static final String USER = "/user";
    public static final String NEW = "/new";

    public static final String BY_ID = "/{id}";
    public static final String BY_LOGIN = "/getUserByLogin/{login}";
    public static final String AUTHENTICATE = "/authenticate/{email}";
    public static final String GET_AUTH = "/getauth/{login}/{password}";
    public static final String SEARCH = "/search/{login}";

    public static class URL {
        public static String GET_BY_ID = build(USER, BY_ID);
        public static String GET_BY_LOGIN = build(USER, BY_LOGIN);
        public static String USER_AUTHENTICATE = build(USER, AUTHENTICATE);
    }

}
