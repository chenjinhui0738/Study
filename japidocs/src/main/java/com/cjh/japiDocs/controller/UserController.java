package com.cjh.japiDocs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 */
@RequestMapping("/api/user/")
@RestController
public class UserController {
    /*

     *//**
     * 用户列表
     * @param listForm
     *//*
    @RequestMapping(path = "list", method = {RequestMethod.GET,  RequestMethod.POST}  )
    public ApiResult<PageResult<UserVO>> list(UserListForm listForm){
        return null;
    }

    *//**
     * 保存用户
     * @param userForm
     *//*
    @PostMapping(path = "save")
    public ApiResult<UserVO> saveUser(@RequestBody UserForm userForm){
        return null;
    }

    *//**
     * 删除用户
     * @param userId 用户ID
     *//*
    @PostMapping("delete")
    public ApiResult deleteUser(@RequestParam Long userId){
        return null;
    }*/
}
