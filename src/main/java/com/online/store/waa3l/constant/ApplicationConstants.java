package com.online.store.waa3l.constant;

public interface ApplicationConstants {
    interface EMAIL {
        String SSL = "mail.smtp.ssl.trust";
        String PORT = "mail.smtp.port";
        String IS_AUTH = "mail.smtp.auth";
        String TLS_ENABLE = "mail.smtp.starttls.enable";
        String HOST = "mail.host";
        String USERNAME = "mail.username";
        String PASSWORD = "mail.password";
        String FROM_ADDRESS = "mail.from.address";
        String FROM_NAME = "mail.from.name";
        String UTF8 = "UTF-8";
        String SMTP = "smtp";
        String UTF8_WEB_FORMAT_CONTENT = "text/html; charset=utf-8";
    }

    interface SESSION_ATTRIBUTES {
        String SHOPPING_CART = "shoppingCart";
    }

    int ACCUMULATED_POINT_EXCHANGE_RATE = 10;
    int EQUAL_POINT_EXCHANGE_RATE = 1;
}
