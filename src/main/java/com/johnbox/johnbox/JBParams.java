package com.johnbox.johnbox;

public enum JBParams {
    TEXT_CREATE(null, null),
    TEXT_SET(null, null),
    TEXT_UPDATE(null, null),
    TEXT_GET(null, null),
    NUMBER_CREATE(null, null),
    NUMBER_SET(null, null),
    NUMBER_UPDATE(null, null),
    NUMBER_GET(null, null),
    OBJECT_CREATE(null, null),
    OBJECT_SET(null, null),
    OBJECT_UPDATE(null, null),
    OBJECT_GET(null, null),
    ROOM_EXIT(null, null),
    LOCK(null, null),
    DROP(null, null),
    OTHER(null, null);

    public JBCreateParams create_params;
    public String key_param;

    private JBParams() {
    }

    private JBParams(JBCreateParams create_params, String key_param) {
        this.create_params = create_params;
        this.key_param = key_param;
    }

    public String scope() {
        switch (this) {
            case TEXT_CREATE:
            case TEXT_SET:
            case TEXT_UPDATE:
            case TEXT_GET:
                return "text";
            case OBJECT_CREATE:
            case OBJECT_SET:
            case OBJECT_UPDATE:
            case OBJECT_GET:
                return "object";
            case NUMBER_CREATE:
            case NUMBER_SET:
            case NUMBER_UPDATE:
            case NUMBER_GET:
                return "number";
            case ROOM_EXIT:
                return "room";
            case LOCK:
                return "lock";
            case DROP:
                return "drop";
            default:
                return "other";
        }
    }
}
