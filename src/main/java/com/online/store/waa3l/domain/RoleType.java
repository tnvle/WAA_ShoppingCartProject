package com.online.store.waa3l.domain;

public enum RoleType {
    ROLE_ADMIN(1), ROLE_SELLER(2), ROLE_BUYER(3);

    private int value;

    RoleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
