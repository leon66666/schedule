package javacommon.base;

import com.hoomsun.page.Page;
import com.hoomsun.page.PageRequest;
import com.hoomsun.page.util.ConvertRegisterHelper;
import com.hoomsun.page.util.PageRequestFactory;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import zhongqiu.javautils.UtilTools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public abstract class BaseStruts2Action extends ActionSupport implements RequestAware {
    private static final long serialVersionUID = 1336106705988251461L;
    protected final static String CREATED_SUCCESS = "创建成功";
    protected final static String UPDATE_SUCCESS = "更新成功";
    protected final static String DELETE_SUCCESS = "删除成功";
    protected final static String CREATED_FAIL = "创建失败";
    protected final static String UPDATE_FAIL = "更新失败";
    protected final static String DELETE_FAIL = "删除失败";

    @SuppressWarnings("rawtypes")
    protected Map requestMap = null;
    static {
        //注册converters
        ConvertRegisterHelper.registerConverters();
    }

    public void copyProperties(Object target, Object source) {
        BeanUtils.copyProperties(target, source);
    }

    @SuppressWarnings("rawtypes")
    public void setRequest(Map request) {
        this.requestMap = request;
    }

    @SuppressWarnings("rawtypes")
    public void savePage(Page page, PageRequest pageRequest) {
        savePage("", page, pageRequest);
    }

    /**
     * 用于一个页面有多个extremeTable是使用
     * 
     * @param tableId
     *            等于extremeTable的tableId属性
     */
    @SuppressWarnings("rawtypes")
    public void savePage(String tableId, Page page, PageRequest pageRequest) {
        Assert.notNull(tableId, "tableId must be not null");
        Assert.notNull(page, "page must be not null");
        getRequest().setAttribute(tableId + "page", page);
        getRequest().setAttribute(tableId + "totalRows", new Integer(page.getTotalCount()));
        getRequest().setAttribute(tableId + "pageRequest", pageRequest);
        getRequest().setAttribute(tableId + "query", pageRequest);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T extends PageRequest> T newQuery(Class<T> queryClazz, String defaultSortColumns) {
        PageRequest query = PageRequestFactory.bindPageRequest(BeanUtils.instantiateClass(queryClazz), ServletActionContext.getRequest(), defaultSortColumns);
        return (T) query;
    }

    public boolean isNullOrEmptyString(Object o) {
        return UtilTools.isNullOrEmpty(o);
    }

    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

}
