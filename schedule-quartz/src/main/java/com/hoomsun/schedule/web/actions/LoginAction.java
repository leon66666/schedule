/*
 * 
 * 
 */
package com.hoomsun.schedule.web.actions;

import com.hoomsun.model.Staff;
import com.hoomsun.page.util.HttpTools;
import com.hoomsun.schedule.service.LoginService;
import com.hoomsun.service.UserService;
import com.hoomsun.util.LoginUtil;
import com.hoomsun.util.UtilTools;
import javacommon.base.BaseStruts2Action;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * @description 登录action
 */
public class LoginAction extends BaseStruts2Action {
    protected static final String SUCCESS = "/WEB-INF/jsp/index.jsp";
    protected static final String ERROR = "/WEB-INF/jsp/login.jsp";
    protected static final String INPUT = "/WEB-INF/jsp/login.jsp";
    private static final long serialVersionUID = 4305445079780144035L;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    /**
     * 登陆用户名
     **/
    private String username;
    /**
     * 登陆密码
     **/
    private String password;

    /**
     * 注销系统
     */
    public String logOut() {
        HttpSession session = getRequest().getSession();
        session.removeAttribute("logingUser");
        session.removeAttribute("isAdmin");
        session.invalidate();
        return this.execute();
    }

    /*
     * 用户登录 
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() {
        try {
            HttpSession session = getRequest().getSession();
            Staff user = (Staff) session.getAttribute("logingUser");
            if (user != null) {
                return SUCCESS;
            } else {
                if (UtilTools.isNullOrEmpty(username) || UtilTools.isNullOrEmpty(password)) {
                    return ERROR;
                } else {
//                    user = userService.loginForInternal(username, password);
                    user = userService.getStaffByUsername(username);
                    if(null!=user){
                        if(!user.isEnabled()){
                            return ERROR;
                        }
                        boolean flag =userService.isPwd(password,user);
                        if(!flag){
                            return ERROR;
                        }
                        LoginUtil.user = user;
                        LoginUtil.loginIp = HttpTools.getIpAddr(getRequest());
                        session.setAttribute("logingUser", user);
                        return SUCCESS;
                    }
                    // 查询用户是否合法
//                    if (!isNullOrEmptyString(user)) {
////                        Staff staff = loginService.loginForInternal(user.getUsername());
//                        // 验证白名单
//                        if (!isNullOrEmptyString(staff)) {
//                            // TODO 是否为管理员用户
//                            session.setAttribute("isAdmin", staff.isEnabled());
//                        } else {
//                            getRequest().setAttribute("loginFail", "用户不允许登陆");
//                            return ERROR;
//                        }
//                    } else {
//                        getRequest().setAttribute("loginFail", "用户名密码不正确");
//                        return ERROR;
//                    }
//                    LoginUtil.user = user;
//                    LoginUtil.loginIp = HttpTools.getIpAddr(getRequest());
//                    session.setAttribute("logingUser", user);
//                    return SUCCESS;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return ERROR;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
