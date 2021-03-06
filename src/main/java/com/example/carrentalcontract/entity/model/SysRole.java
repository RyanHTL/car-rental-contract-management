package com.example.carrentalcontract.entity.model;

import com.example.carrentalcontract.common.DbPageParameter;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.jws.HandlerChain;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色表(SysRole)实体类
 *
 * @author makejava
 * @since 2020-12-27 22:11:56
 */
@Accessors(chain = true)
@Data
@Table(name = "sys_role")
public class SysRole extends DbPageParameter implements Serializable {
    private static final long serialVersionUID = -78784730211500405L;
    /**
    * 角色id
    */
    @Id
    private Long id;
    /**
    * 角色名
    */
    @Column(name = "role_name")
    private String roleName;
    /**
     * 角色中文名
     */
    @Column(name = "role_ZH")
    private String roleZH;
    /**
     * 备注
     */
    private String remark;
    /**
     * 标识
     */
    private Integer flag;
    /**
     * 排序
     */
    private Integer sort;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}