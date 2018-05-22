package com.hoomsun.schedule.dao;

import com.hoomsun.model.Staff;
import com.hoomsun.util.JDomUtil;
import com.hoomsun.util.UtilTools;
import org.jdom.Attribute;
import org.jdom.Element;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A data access object (DAO) providing persistence and search support for
 * TaskTimer entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * 
 */
@Repository
public class LoginDAO {
    public final String FILE_PATH = "user4login.xml";
    public final String USER_NAME_KEY = "name";
    public final String USER_ISADMIN_KEY = "isAdmin";

    public Staff getUserByUsernamePassword(String username) {
        // 读取所有二级元素
        List<Element> list = JDomUtil.getAllSecondLevelElement(FILE_PATH);
        for (Element e : list) {
            // 获取用户名
            Attribute attr = e.getAttribute(USER_NAME_KEY);
            // 如果用户名相同
            if (!UtilTools.isNullOrEmpty(attr) && username.equalsIgnoreCase(attr.getValue())) {
                // 获取密码 和启用状态
                Element eleIsAdmin = JDomUtil.getSubordinateElementByKey(e, USER_ISADMIN_KEY);
                Staff staff = new Staff();
                staff.setUsername(username);
                if (Boolean.TRUE.toString().equals(eleIsAdmin.getValue().trim())) {
                    staff.setEnabled(true);
                } else {
                    staff.setEnabled(false);
                }
                return staff;
            }

        }
        return null;
    }
}