package com.example.carrentalcontract.sercive.impl;

import com.example.carrentalcontract.common.DbServiceImpl;
import com.example.carrentalcontract.common.Result;
import com.example.carrentalcontract.entity.model.SysMenu;
import com.example.carrentalcontract.entity.model.SysRole;
import com.example.carrentalcontract.entity.model.SysUser;
import com.example.carrentalcontract.entity.response.MenuResponseInfo;
import com.example.carrentalcontract.entity.view.SysMenuView;
import com.example.carrentalcontract.mapper.SysMenuMapper;
import com.example.carrentalcontract.sercive.SysMenuService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 菜单
 * @author: 黄天亮
 * @create: 2021-01-05 17:06
 **/
@Service("sysMenuService")
public class SysMenuServiceImpl extends DbServiceImpl<SysMenu> implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public Result<SysMenu> insert(SysMenu sysMenu) {
        return super.insert(sysMenu);
    }

    @Override
    public Result update(SysMenu sysMenu) {
        return super.update(sysMenu);
    }

    @Override
    public Result delete(SysMenu menu) {
        return super.destroy(menu);
    }

    @Override
    public Result<List<SysMenu>> selectAll(SysMenu sysMenu) {
        return null;
    }

    @Override
    public Result save(List<SysMenuView> menus) {
        return null;
    }

    @Override
    public Result<List<SysMenuView>> selectMenusByRole(SysRole role) {
        return null;
    }
}
