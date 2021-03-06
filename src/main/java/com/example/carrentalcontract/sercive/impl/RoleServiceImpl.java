package com.example.carrentalcontract.sercive.impl;

import com.example.carrentalcontract.common.DbServiceImpl;
import com.example.carrentalcontract.common.Result;
import com.example.carrentalcontract.common.translate.ObjectMapper;
import com.example.carrentalcontract.entity.model.SysApi;
import com.example.carrentalcontract.entity.model.SysMenu;
import com.example.carrentalcontract.entity.model.SysRole;
import com.example.carrentalcontract.entity.model.SysUser;
import com.example.carrentalcontract.entity.response.MenuResponseInfo;
import com.example.carrentalcontract.entity.response.SysApiResponseInfo;
import com.example.carrentalcontract.entity.response.SysMenuResponseInfo;
import com.example.carrentalcontract.entity.response.SysRoleResponseInfo;
import com.example.carrentalcontract.entity.request.SysUserRequest;
import com.example.carrentalcontract.mapper.RoleMapper;
import com.example.carrentalcontract.mapper.SysApiMapper;
import com.example.carrentalcontract.mapper.SysMenuMapper;
import com.example.carrentalcontract.sercive.RoleService;
import com.example.carrentalcontract.sercive.SysApiService;
import com.example.carrentalcontract.sercive.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2020-12-27 22:11:56
 */
@Service("roleService")
public class RoleServiceImpl extends DbServiceImpl<SysRole> implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysApiService apiService;
    @Resource
    private SysMenuService menuService;
    @Resource
    private SysApiMapper sysApiMapper;
    @Resource
    protected SysMenuMapper menuMapper;

    @Override
    public Result<List<SysRole>> findAll() {
        // return Result.success(roleMapper.findRoleByUserName("111"));
        return super.selectAll();
    }

    @Override
    public Result insert(SysRole role) {
        return super.insert(role);
    }

    @Override
    @Transactional
    public Result insertUserRole(SysUserRequest user) {
        roleMapper.deleteRoleByUser(user);
        return Result.success(roleMapper.insertUserAndRole(user.getRoleList(), user.getId()));
    }

    @Override
    public Result<SysRoleResponseInfo> findAllSecurity() {
        SysRoleResponseInfo roleResponseInfo = new SysRoleResponseInfo();
        List<SysMenu> sysMenus = sysMenuMapper.selectAll();
        List<SysMenuResponseInfo> menuResponseInfos = ObjectMapper.clone(sysMenus, SysMenuResponseInfo.class);
        List<SysApi> sysApis = sysApiMapper.selectAll();
        List<SysApiResponseInfo> apiResponseInfos = ObjectMapper.clone(sysApis, SysApiResponseInfo.class);

        List<SysMenuResponseInfo> menus = getMenuChildren(0L, menuResponseInfos);
        List<SysApiResponseInfo> apis = getApiChildren(0L, apiResponseInfos);
        roleResponseInfo.setMenuResponseInfos(menus);
        roleResponseInfo.setApiResponseInfos(apis);

        return Result.success(roleResponseInfo);
    }

    @Override
    public Result<List<SysMenuResponseInfo>> findMenuTree() {
        List<SysMenu> sysMenus = sysMenuMapper.selectAll();
        List<SysMenuResponseInfo> menuResponseInfos = ObjectMapper.clone(sysMenus, SysMenuResponseInfo.class);
        List<SysMenuResponseInfo> menus = getMenuChildren(0L, menuResponseInfos);
        return Result.success(menus);
    }

    /**
     * 分配权限
     *
     * @param roleResponseInfo 角色权限
     * @return
     */
    @Override
    @Transactional
    public Result<SysRoleResponseInfo> assignPermission(SysRoleResponseInfo roleResponseInfo) {
        List<Long> apiIds = roleResponseInfo.getApiIds();
        List<Long> menuIds = roleResponseInfo.getMenuIds();
        roleMapper.deleteApiIds(roleResponseInfo);
        roleMapper.deleteMenuIds(roleResponseInfo);
        if (roleResponseInfo.getMenuIds().size() > 0) {
            roleMapper.insertBatchMenuPermission(roleResponseInfo);
        }
        if (roleResponseInfo.getApiIds().size() > 0) {
            roleMapper.insertBatchApiPermission(roleResponseInfo);
        }
        return Result.success();
    }

    @Override
    public Result<List<SysUser>> findUsersByRole(SysRole role) {
        List<SysUser> userList = roleMapper.findUsersByRole(role);
        return Result.success(userList);
    }

    @Override
    public Result<List<SysRole>> findRolesByUser(SysUser user) {
        List<SysRole> roleList = roleMapper.findRolesByUser(user);
        return Result.success(roleList);
    }

    @Override
    public Result<List<MenuResponseInfo>> findUserMenu(SysUser user) {
        List<MenuResponseInfo> menuResponseInfos = new ArrayList<>();
        List<SysRole> roles = roleMapper.findRoleByUserName(user.getUsername());
        List<SysMenu> menus = menuMapper.findSysMenuByRoleIds(roles);
        List<MenuResponseInfo> infos = translateMenu(0L, menus);
        return Result.success(infos);
    }

    private List<MenuResponseInfo> translateMenu(Long parentId, List<SysMenu> menuList) {
        List<MenuResponseInfo> children = new ArrayList<>();
        menuList.stream().filter(item -> {
            return item.getMenuPid().equals(parentId);
        }).forEach(item -> {
            MenuResponseInfo menu = new MenuResponseInfo();
            menu.setIcon(item.getIcon());
            menu.setPath(item.getUrl());
            menu.setTitle(item.getMenuName());
            menu.setSort(item.getSort());
            List<MenuResponseInfo> infoList = translateMenu(item.getId(), menuList);
            if (infoList.size() > 0) {
                menu.setChildren(infoList);
            }
            children.add(menu);
        });
        if (children.size() > 0) {
            children.sort(Comparator.comparing(MenuResponseInfo::getSort));
        }
        return children;
    }

    @Override
    @Transactional
    public Result deleteRole(SysRole role) {
        roleMapper.deleteApiByRole(role);
        roleMapper.deleteMenuByRole(role);
        roleMapper.deleteRoleUser(role);
        return super.destroy(role);
    }

    private List<SysApiResponseInfo> apiTreeToList(List<SysApiResponseInfo> tree) {
        List<SysApiResponseInfo> list = new ArrayList<>();
        tree.forEach(item -> {
            list.add(item);
            if (item.getChildren().size() > 0) {
                apiTreeToList(item.getChildren());
            }
        });
        return list;
    }

    private List<SysMenuResponseInfo> menuTreeToList(List<SysMenuResponseInfo> tree) {
        List<SysMenuResponseInfo> list = new ArrayList<>();
        tree.forEach(item -> {
            list.add(item);
            if (item.getChildren().size() > 0) {
                menuTreeToList(item.getChildren());
            }
        });
        return list;
    }

    @Override
    public Result<List<SysApiResponseInfo>> findApiTree() {
        List<SysApi> sysApis = sysApiMapper.selectAll();
        List<SysApiResponseInfo> apiResponseInfos = ObjectMapper.clone(sysApis, SysApiResponseInfo.class);
        List<SysApiResponseInfo> apis = getApiChildren(0L, apiResponseInfos);
        return Result.success(apis);
    }

    private List<SysMenuResponseInfo> getMenuChildren(Long parentId, List<SysMenuResponseInfo> menuResponseInfos) {
        return menuResponseInfos.stream().filter(item -> {
            if (item.getMenuPid().equals(parentId)) {
                item.setChildren(getMenuChildren(item.getId(), menuResponseInfos));
                return true;
            }
            ;
            return false;
        }).sorted(Comparator.comparing(SysMenuResponseInfo::getSort)).collect(Collectors.toList());
    }

    private List<SysApiResponseInfo> getApiChildren(Long parentId, List<SysApiResponseInfo> apiResponseInfos) {
        List<SysApiResponseInfo> children = apiResponseInfos.stream().filter(item -> {
            if (item.getApiPid() == parentId) {
                item.setChildren(getApiChildren(item.getId(), apiResponseInfos));
                return true;
            }
            ;
            return false;
        }).collect(Collectors.toList());
        ;
        return children;
    }

    @Override
    public Result<SysRoleResponseInfo> findSecurityByRoles(List<SysRole> roles) {
        SysRoleResponseInfo roleResponseInfo = new SysRoleResponseInfo();
        List<SysMenuResponseInfo> menuResponseInfos = new ArrayList<>();
        List<SysApiResponseInfo> apiResponseInfos = new ArrayList<>();

        List<SysApi> sysApis = sysApiMapper.findSysApisByRolesIds(roles);
        List<SysMenu> sysMenus = sysMenuMapper.findSysMenuByRoleIds(roles);
        menuResponseInfos = ObjectMapper.clone(sysMenus, SysMenuResponseInfo.class);
        apiResponseInfos = ObjectMapper.clone(sysApis, SysApiResponseInfo.class);
        roleResponseInfo.setMenuResponseInfos(menuResponseInfos);
        roleResponseInfo.setApiResponseInfos(apiResponseInfos);

        return Result.success(roleResponseInfo);
    }

    @Override
    public Result<SysRole> selectByPrimaryKey(Long id) {

        return super.selectByPrimaryKey(id);
    }


}