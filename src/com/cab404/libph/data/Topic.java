package com.cab404.libph.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * @author cab404
 */
public class Topic {
    private static final long serialVersionUID = 0L;


    public String title;

    public Profile author;

    public Blog blog;


    public String text;

    public List<String> tags;


    public Calendar date;

    public int id;


    public boolean in_favourites = false;

    public boolean is_poll = false;
    public boolean is_pollFinished = false;

    public List<KV<String, Integer>> pollData;

    public int comments = 0, comments_new = 0;

    public Topic() {
        tags = new ArrayList<>();
        blog = new Blog();
        author = new Profile();
    }
}
