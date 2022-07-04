package com.cjh.swagger.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 *
 * @ApiModel()用于类 ；表示对类进行说明，用于参数用实体类接收
 * value–表示对象名
 * description–描述
 * 都可省略
 * @ApiModelProperty()用于方法，字段；表示对model属性的说明或者数据操作更改 value–字段说明
 * name–重写属性名字
 * dataType–重写属性类型
 * required–是否必填
 * example–举例说明
 * hidden–隐藏
 * @ApiIgnore()用于类或者方法上，可以不被swagger显示在页面上 比较简单, 这里不做举例
 */
@ApiModel(value = "user对象", description = "用户对象user")
public class User implements Serializable {
    private static final long serialVersionUID = -3584421540796682968L;
    @ApiModelProperty(value = "用户名", name = "username", example = "xingguo")
    private String userName;
    @ApiModelProperty(value = "状态", name = "state", required = true)
    private Integer state;
    private String password;
    private String nickName;
    private Integer isDeleted;
    @ApiModelProperty(value = "id数组", hidden = true)
    private String[] ids;
    private List<String> idList;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }
}
