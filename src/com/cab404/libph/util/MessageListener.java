package com.cab404.libph.util;

import org.json.simple.JSONObject;

/**
 * Вся его работа - принимать строки о том,
 * что нельзя давать комментариям больше одного минуса
 * и заходить в Табун с неправильным паролем.
 */
public interface MessageListener {
    public void show(JSONObject parsed);
}
