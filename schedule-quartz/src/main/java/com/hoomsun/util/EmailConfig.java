package com.hoomsun.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailConfig {

    private static Properties props;

    /**
     * 民生中转帐户余额提醒邮件收件人列表
     */
    public static String CMBC_BALANCE_MAIL_TO;

    static {
        props = new Properties();
        InputStream fis = null;
        try {
            fis = EmailConfig.class.getResourceAsStream("/email.properties");
            props.load(fis);
            CMBC_BALANCE_MAIL_TO = props.getProperty("cmbc.balance.mail.to");
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
