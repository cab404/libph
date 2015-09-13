package com.cab404.libph.data;

import com.cab404.libph.util.JSONable;

/**
 * Лента.
 *
 * @author cab404
 */
public class TimelineEntry extends JSONable {
    @JSONField
    public Profile commenter;
    @JSONField
    public Topic topic;
    @JSONField
    public int comment_id;

}
