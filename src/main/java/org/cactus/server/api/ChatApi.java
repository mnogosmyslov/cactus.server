package org.cactus.server.api;

public class ChatApi extends AbstractApi {

    public static final String CHAT = "/chat";

    public static final String BY_ID = "/{id}";

    public static class URL {
        public static String GET_BY_ID = build(CHAT, BY_ID);
    }

}
