package com.hoomsun.util;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 这个类提供一些 JDom 对象常用的方法。
 */
public class JDomUtil {

    /**
     * 根据指定路径的XML文件建立JDom对象
     * 
     * @param filePath
     *            XML文件的路径
     * @return 返回建立的JDom对象，建立不成功返回null 。
     */
    public static Document buildFromFile(String filePath) {
        try {
            SAXBuilder builder = new SAXBuilder();
            builder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            Document anotherDocument = builder.build(new File(filePath));
            return anotherDocument;
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根文件路径获取所有二级对象
     * 
     * @param xmlString
     *            XML格式的字符串
     * @return list
     */

    @SuppressWarnings("unchecked")
    public static List<Element> getAllSecondLevelElement(String filePath) {
        Document doc = buildFromFile(JDomUtil.class.getResource("/").getPath() + filePath);
        Element e = doc.getRootElement();
        if (!UtilTools.isNullOrEmpty(e)) {
            return e.getChildren();
        }
        return null;
    }

    /**
     * 根标签名获取下级对象
     * 
     * @return list
     */
    public static Element getSubordinateElementByKey(Element e, String key) {
        List<?> list = e.getChildren(key);
        if (!UtilTools.isNullOrEmpty(list)) {
            return (Element) list.get(0);
        }
        return null;
    }

    /**
     * 获取所有下级对象
     * 
     * @return list
     */
    @SuppressWarnings("unchecked")
    public static List<Element> getAllSubordinateElement(Element e) {
        return e.getChildren();
    }

}