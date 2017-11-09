package com.cab404.libph.data;

import java.util.Calendar;

/**
 * PaWPoL с рейтингом, описанием и читателями.
 *
 * @author cab404
 */
public class Blog {
    private static final long serialVersionUID = 0L;


    public String
            name,
            url_name,
            about,
            icon;

    public int
            id,
            readers;

    public Calendar
            creation_date;




    public boolean
            restricted = false;

    public Blog() {
    }

    public Blog(String url) {
        this.url_name = url;
    }

    public String resolveURL() {
        return "/blog/" + url_name + "/";
    }

}
