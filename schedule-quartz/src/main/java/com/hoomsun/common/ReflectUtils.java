package com.hoomsun.common;

import com.hoomsun.util.UtilTools;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

public class ReflectUtils {

    /**
     * 获取 目标对象
     * 
     * @param proxy
     *            代理对象
     * @return
     * @throws Exception
     */
    public static Object getTarget(Object proxy) throws Exception {

        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;//不是代理对象  
        }

        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else { //cglib  
            return getCglibProxyTargetObject(proxy);
        }

    }

    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        return target;
    }

    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);

        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();

        return target;
    }

    /*public Object getTarget(Object proxy)*/
    /**
     * @param str
     *            根据类的全名获取在spring中注入的id（默认约定）
     * @return 类在spring注入的id名
     */
    public static String getClassName(String str) {
        if (!UtilTools.isNullOrEmpty(str)) {
            // 获取最后一个'.'的位置
            int pos = str.lastIndexOf(".");
            if (str.lastIndexOf(".") > 0) {
                // 截取包名
                return str.substring(pos + 1);
            }
        }
        return "";

    }

    /**
     * @param str
     *            根据类的全名获取在spring中注入的id（默认约定）
     * @return 类在spring注入的id名
     */
    public static String getClassNameInIoc(String str) {
        if (!UtilTools.isNullOrEmpty(str)) {
            // 去掉第一个'$'开始的所有字符
            int pos = str.indexOf("$");
            if (pos > 0) {
                str = str.substring(0, pos);
            }
            // 获取最后一个'.'的位置
            pos = str.lastIndexOf(".");
            if (pos > 0) {
                // 截取包名
                String tempName = str.substring(pos + 1);
                if (tempName.length() > 1) {
                    // 截取首字母
                    String firstChar = tempName.substring(0, 1);
                    return firstChar.toLowerCase() + tempName.substring(1);
                }
            }
        }
        return "";

    }
}