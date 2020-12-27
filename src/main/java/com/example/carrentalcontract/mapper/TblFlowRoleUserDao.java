package com.example.carrentalcontract.mapper;


import com.example.carrentalcontract.entity.TblFlowRoleUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 流程角色_员工表(TblFlowRoleUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-27 22:11:56
 */
public interface TblFlowRoleUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TblFlowRoleUser queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TblFlowRoleUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tblFlowRoleUser 实例对象
     * @return 对象列表
     */
    List<TblFlowRoleUser> queryAll(TblFlowRoleUser tblFlowRoleUser);

    /**
     * 新增数据
     *
     * @param tblFlowRoleUser 实例对象
     * @return 影响行数
     */
    int insert(TblFlowRoleUser tblFlowRoleUser);

    /**
     * 修改数据
     *
     * @param tblFlowRoleUser 实例对象
     * @return 影响行数
     */
    int update(TblFlowRoleUser tblFlowRoleUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}