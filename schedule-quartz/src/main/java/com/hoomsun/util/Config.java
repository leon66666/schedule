package com.hoomsun.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static Properties props;

    /**
     * 移动端交易状态查询接口地址
     */
    public static String MOBILE_API_PAY_STATUS_URL;

    static {
        props = new Properties();
        InputStream fis = null;
        try {
            fis = Config.class.getResourceAsStream("/config.properties");
            props.load(fis);
            MOBILE_API_PAY_STATUS_URL = props.getProperty("mobile.api.pay.status.url");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
