package com.example.carrentalcontract.controller;


import com.example.carrentalcontract.common.Result;
import com.example.carrentalcontract.entity.model.SysApi;
import com.example.carrentalcontract.entity.model.SysMenu;
import com.example.carrentalcontract.entity.model.SysRole;
import com.example.carrentalcontract.entity.response.SysApiResponseInfo;
import com.example.carrentalcontract.entity.response.SysMenuResponseInfo;
import com.example.carrentalcontract.entity.response.SysRoleResponseInfo;
import com.example.carrentalcontract.sercive.RoleService;
import com.example.carrentalcontract.sercive.SysApiService;
import com.example.carrentalcontract.sercive.SysMenuService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色表(SysRole)表控制层
 *
 * @author makejava
 * @since 2020-12-27 22:11:56
 */
@RestController
@RequestMapping("/api/v1/car")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;
    @Resource
    private SysApiService sysApiService;
    @Resource
    private SysMenuService sysMenuService;

    // --------role
    @PostMapping("/role/find/all")
    public Result<List<SysRole>> findAll() {
        return this.roleService.findAll();
    }

    @PostMapping("/role/find/security")
    public Result<SysRoleResponseInfo> findSecurity() {
        return this.roleService.findAllSecurity();
    }

    @PostMapping("/role/insert")
    public Result<SysRoleResponseInfo> roleInsert() {
        return this.roleService.findAllSecurity();
    }

    @PostMapping("/role/update")
    public Result<SysRoleResponseInfo> roleUpdate() {
        return this.roleService.findAllSecurity();
    }
    @PostMapping("/role/destroy")
    public Result<SysRoleResponseInfo> roleDestroy  () {
        return this.roleService.findAllSecurity();
    }

    // 给角色分配权限
    @PostMapping("/role/assign/permissions")
    public Result<SysRoleResponseInfo> assign(@RequestBody SysRoleResponseInfo roleResponseInfo) {
        return this.roleService.assignPermission(roleResponseInfo);
    }

    // --------menu


    @PostMapping("/menu/find/tree")
    public Result<List<SysMenuResponseInfo>> findMenuTree() {
        return this.roleService.findMenuTree();
    }

    @PostMapping("/menu/find/all")
    public Result<List<SysMenu>> findMenuAll() {
        return this.sysMenuService.selectAll();
    }

    // --------api

    @PostMapping("/api/find/tree")
    public Result<List<SysApiResponseInfo>> findApiTree() {
        return this.roleService.findApiTree();
    }

    @PostMapping("/api/find/all")
    public Result<List<SysApi>> findApiAll() {
        return this.sysApiService.selectAll();
    }

    @PostMapping("/api/find/page")
    public Result<PageInfo<SysApi>> findApiAll(@RequestBody SysApi sysApi) {
        return this.sysApiService.findPage(sysApi);
    }


}