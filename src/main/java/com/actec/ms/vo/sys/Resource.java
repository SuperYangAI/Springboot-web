package com.actec.ms.vo.sys;

import com.actec.ms.vo.BaseEntity;

public class Resource extends BaseEntity {

    public static final int TYPE_BUTTON = 0;
    public static final int TYPE_MENU = 1;

    public static final String METHOD_POST = "POST";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_ALL = "ALL";

    private String url;
    private float orderNum;
    private int type;
    private String method;
    private String remark;
    private int parentId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(float orderNum) {
        this.orderNum = orderNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
