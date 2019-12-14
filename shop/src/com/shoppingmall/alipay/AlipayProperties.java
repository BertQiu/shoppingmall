package com.shoppingmall.alipay;


import java.util.Properties;

/**
 * @author Bert Q
 * ClassName : AlipayProperties
 * Description TODO
 */
public class AlipayProperties {
    public static String APP_ID = "appId";
    public static String PRIVARY_KEY = "privateKey";
    public static String PUBLIC_KEY = "publicKey";
    public static String NOTIFY_URL = "notifyUrl";
    public static String RETURN_URL = "returnUrl";
    public static String SIGN_TYPE = "signType";
    public static String CHARSET = "charset";
    public static String GATEWAY_URL = "gatewayUrl";
    public static String LOG_PATH = "logPath";

    /**
     * 加载属性
     */
    static {
        try {
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("alipay.properties"))
            ;
            APP_ID = properties.getProperty("appId");
            PRIVARY_KEY = properties.getProperty("privateKey");
            PUBLIC_KEY = properties.getProperty("publicKey");
            NOTIFY_URL = properties.getProperty("notifyUrl");
            RETURN_URL = properties.getProperty("returnUr");
            SIGN_TYPE = properties.getProperty("signType");
            CHARSET = properties.getProperty("charset");
            GATEWAY_URL = properties.getProperty("gatewayUrl");
//            LOG_PATH = properties.getProperty("logPath");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
