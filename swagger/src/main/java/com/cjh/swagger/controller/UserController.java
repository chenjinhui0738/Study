package com.cjh.swagger.controller;

import com.cjh.swagger.domain.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * 用户controller
 *
 * @Api() 用于类；表示标识这个类是swagger的资源
 * tags–list说明,方法上的tags需要与类上的值一致，否则会出现多个list,不写则默认一致
 * value–也是说明，可以使用tags替代
 * 但是tags如果有多个值，会生成多个list
 */
@RestController
@RequestMapping(path = "/user")
@Api(value = "用户controller", tags = {"用户操作接口"})
public class UserController {
    /**
     * 获取用户信息
     */
    @GetMapping("/getUserInfo")
    @ResponseBody
    @ApiOperation(value = "获取用户信息", tags = {"用户操作接口"}, notes = "注意问题点")
    public User getUserInfo(@ApiParam(value = "用户名", required = true) @RequestParam(name = "userName", defaultValue = "张三") String userName) {
        User user = new User();
        user.setUserName(userName);
        return user;
    }

    /**
     * @ApiOperation() 用于方法；表示一个http请求的操作
     * value用于方法描述
     * notes用于提示内容
     * tags可以重新分组（视情况而用）
     * @ApiParam() 用于方法，参数，字段说明；表示对参数的添加元数据（说明或是否必填等）
     * name–参数名
     * value–参数说明
     * required–是否必填
     */
    @GetMapping("/getUserInfoCopy")
    @ResponseBody
    @ApiOperation(value = "获取用户信息copy", tags = {"用户操作接口"}, notes = "注意问题点")
    public User getUserInfo(@ApiParam(name = "id", value = "用户id", required = true) Long id, @ApiParam(name = "username", value = "用户名") String userName) {
        User user = new User();
        user.setUserName(userName);
        return user;
    }

    /**
     * @ApiImplicitParam() 用于方法
     * 表示单独的请求参数
     * @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam
     * name–参数ming
     * value–参数说明
     * dataType–数据类型
     * paramType–参数类型
     * example–举例说明
     */
    @ApiOperation("查询测试")
    @GetMapping("select")
    //@ApiImplicitParam(name="name",value="用户名",dataType="String", paramType = "query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名", dataType = "string", paramType = "query", example = "xingguo"),
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "long", paramType = "query")})
    public void select() {
    }
}
