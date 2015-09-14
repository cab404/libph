package com.cab404.libph.pages;

import com.cab404.moonlight.framework.ModularBlockParser;

/**
 * @author cab404
 */
public class MainPage extends BasePage {

    @Override
    public String getURL() {
        return "/";
    }

    @Override
    protected void bindParsers(ModularBlockParser base) {
        super.bindParsers(base);
    }

}
