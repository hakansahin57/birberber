package com.birberber.constants;

public final class Constants {

    //    Pages
    public static final String HOME_PAGE = "home";
    public static final String LOGIN_PAGE = "login";
    public static final String REGISTER_PAGE = "register";
    public static final String APPOINTMENTS_PAGE = "appointments";
    public static final String STORES_PAGE = "stores";
    public static final String PROFILE_PAGE = "profile";
    public static final String PASSWORD_PAGE = "password";

    // Admin Pages
    public static final String ADMIN_PAGE = "/admin/admin";
    public static final String ADMIN_DATA_IMPEX = "/admin/data-impex";



    //    Languages
    public static final String TURKISH_LANGUAGE_ISOCODE = "tr_TR";

    //    Configuration
    public static final String MESSAGESOURCE_BASENAME = "/WEB-INF/messages/messages";
    public static final String DEFAULT_ENCODING = "UTF-8";

    private Constants() {
        //empty to avoid instantiating this constant class
    }

}