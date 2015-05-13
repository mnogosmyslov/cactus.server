package org.cactus.server.api;

public class AuthApi extends AbstractApi {

    public static final String LOGIN_URL = "/api/authentication/login";

    public static final String AUTH = "/api";
    public static final String IS_LOGINED = "/authentication/check";

    public class Parameters {
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String COOKIES = "JSESSIONID";
    }

}
