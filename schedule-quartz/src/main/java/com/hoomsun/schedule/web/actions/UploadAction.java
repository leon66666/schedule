package com.hoomsun.schedule.web.actions;

import javacommon.base.BaseStruts2Action;
import org.apache.struts2.ServletActionContext;

import java.io.*;

/**
 *
 */
public class UploadAction extends BaseStruts2Action {
    protected static final String TEST_JSP = "/index1.jsp";
    protected static final String UPLOAD_PRE_JSP = "/jsp/upload_pre.jsp";
    protected static final String SUCCESS = "/jsp/upload_pre.jsp";
    protected static final String ERROR = "/jsp/upload_pre.jsp";
    protected static final String INPUT = "/jsp/upload_pre.jsp";
    private static final long serialVersionUID = 4305445079780144035L;

    private String username;
    private String password;
    private File file;
    /**
     * 上传文件名称
     **/
    private String fileFileName;
    /**
     * 上传文件类型
     **/
    private String fileContentType;

    @Override
    public String execute() {
        if (isNullOrEmptyString(fileFileName) || isNullOrEmptyString(file))
            return ERROR;
        int start = fileFileName.lastIndexOf("//");
        fileFileName = fileFileName.substring(start + 1);
        start = fileFileName.lastIndexOf(".");
        File fileTemp = new File(ServletActionContext.getServletContext().getRealPath("upload"), fileFileName);

        InputStream is = null;
        OutputStream os = null;
        try {
            fileTemp.getParentFile().mkdirs();
            is = new FileInputStream(file);
            os = new FileOutputStream(fileTemp);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                    if (is != null) {
                        is.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        getRequest().setAttribute("uploadFlag", "上传成功");
        return SUCCESS;
    }

    public String pre() {
        return UPLOAD_PRE_JSP;
    }

    public String test() {
        return TEST_JSP;
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

}
