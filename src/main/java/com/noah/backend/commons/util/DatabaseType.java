package com.noah.backend.commons.util;

public enum DatabaseType {

    //어감이 별로임..
    MASTER("MASTER"), SLAVE("SLAVE");

    private String type;

    DatabaseType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}