package javacommon.base.interceptor;

import com.hoomsun.model.Staff;
import com.hoomsun.util.UtilTools;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆拦截器
 *
 * 
 */
public class LoginInterceptor implements Interceptor {
    /**
     *
     */
    private static final long serialVersionUID = 7931772178599271131L;
    static Log log = LogFactory.getLog(LoginInterceptor.class);
    private static final String VISTER_JSP = "/index.jsp";
    private static final String TASK_MGMT_URL = "/api/taskMgmt.action";

    public String intercept(ActionInvocation invocation) throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();
        Staff user = (Staff) request.getSession().getAttribute("logingUser");

        // 任务管理操作,用户权限验证入到Action里面进行
        if (request.getRequestURI().startsWith(TASK_MGMT_URL)
                || request.getRequestURI().indexOf("QuickTest") >= 0
                || request.getRequestURI().indexOf("DataRepair") >= 0
                || request.getRequestURI().indexOf("ExecJob") >= 0) {
            String result = invocation.invoke();
            return result;
        }

        // 没有登陆用户跳回登陆页
        if (UtilTools.isNullOrEmpty(user)) {
            return VISTER_JSP;
        } else {
            String result = invocation.invoke(); // 登陆用户继续执行
            return result;
        }
    }

    @Override
    public void destroy() {


    }

    @Override
    public void init() {


    }

}
