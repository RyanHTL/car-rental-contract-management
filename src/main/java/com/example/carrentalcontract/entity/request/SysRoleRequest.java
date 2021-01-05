package com.example.carrentalcontract.entity.request;

import com.example.carrentalcontract.entity.model.Permission;
import com.example.carrentalcontract.entity.model.SysRole;
import lombok.Data;

import java.util.List;

/**
 * @Author admin
 * @Description //TODO
 * @date 2021/1/3 17:58
 * @Version 1.0
 **/
@Data
public class SysRoleRequest extends SysRole {

    /**
     * 权限列表
     */
    private List<Permission> permissionList;

}