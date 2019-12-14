package com.shoppingmall.common;

import java.util.Properties;

/**
 * @author Bert Q
 * ClassName : CommonProperties
 * Description TODO
 */
public class CommonProperties {
    public static String WEBSITE_URL = "";

    /**
     * 加载属性
     */
    static {
        try {
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("common.properties"));
            WEBSITE_URL = properties.getProperty("website_url");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
