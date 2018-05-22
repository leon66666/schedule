package com.hoomsun.util;

import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlRemoveUtils {

    private static int indexOfByRegex(String input, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        if (m.find()) {
            return m.start();
        }
        return -1;
    }

    /**
     * 去除select 子句，未考虑union的情况
     * 
     * @param sql
     * @return 去除sql语句from前面串 便于拼接count(*)
     */
    public static String removeSelect(String sql) {
        Assert.hasText(sql);
        int beginPos = indexOfByRegex(sql.toLowerCase(), "\\sfrom\\s");
        Assert.isTrue(beginPos != -1, " sql : " + sql + " must has a keyword 'from'");
        return sql.substring(beginPos);
    }

    /**
     * 去除orderby 子句
     * 
     * @param sql
     *            原sql语句
     * @return 返回去除排序的sql语句
     */
    public static String removeOrders(String sql) {
        Assert.hasText(sql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String removeFetchKeyword(String sql) {
        return sql.replaceAll("(?i)fetch", "");
    }

    public static String removeXsqlBuilderOrders(String string) {
        Assert.hasText(string);
        Pattern p = Pattern.compile("/~.*order\\s*by[\\w|\\W|\\s|\\S]*~/", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return removeOrders(sb.toString());
    }

}
