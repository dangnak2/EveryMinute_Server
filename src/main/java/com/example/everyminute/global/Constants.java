package com.example.everyminute.global;

public class Constants {
    public static final String TIME_PATTERN = "HH:mm";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DATE_HOUR_PATTERN = "yyyy-MM-dd HH";

    public static class JWT{
        public static final String AUTHORIZATION_HEADER = "Authorization";
        public static final String BEARER_PREFIX = "bearer ";
        public static final String CLAIM_NAME = "userIdx";
        public static final String LOGOUT = "logout";
        public static final String SIGNOUT = "signout";
    }
    public static class REDIS{
        public static final String FEED_KEY = "userId::";
    }


}
