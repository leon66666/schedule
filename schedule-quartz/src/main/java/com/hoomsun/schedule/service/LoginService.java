package com.hoomsun.schedule.service;

import com.hoomsun.exception.ServiceException;
import com.hoomsun.model.Staff;
import com.hoomsun.schedule.dao.LoginDAO;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = ServiceException.class)
public class LoginService {

	@Autowired
	private LoginDAO loginDAO;

	// 根据用户名密码查找用户
    @Transactional(readOnly=true)
    public Staff loginForInternal(String username)
            throws HttpRPCException {
        if (StringUtil.isEmpty(username)) {
            return null;
        }
        Staff user = loginDAO.getUserByUsernamePassword(username);

            return user;
    }

}
