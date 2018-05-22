package com.hoomsun.schedule.web.actions;

import javacommon.base.BaseStruts2Action;

import javax.servlet.http.HttpSession;

/**
 * @description 登录action
 */
public class LoginAction extends BaseStruts2Action {
    protected static final String SUCCESS = "/WEB-INF/jsp/index.jsp";
    protected static final String ERROR = "/WEB-INF/jsp/login.jsp";
    protected static final String INPUT = "/WEB-INF/jsp/login.jsp";
    private static final long serialVersionUID = 4305445079780144035L;

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
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
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
