package com.company;

public class RadioHandler {

    String name;
    String url;


    RadioHandler( String name, String url) {
        this.name = name;
        this.url = url;

    }
    RadioHandler() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
