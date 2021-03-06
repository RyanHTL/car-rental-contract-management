package com.example.carrentalcontract.entity.model;

import com.example.carrentalcontract.common.DbPageParameter;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @description:
 * @author: 黄天亮
 * @create: 2021-01-08 16:00
 **/
@Data
@Accessors(chain = true)
@Table(name = "sys_resource")
public class SysResource extends DbPageParameter {
    @Id
    private Long id;
    @Column(name = "old_filename")
    private String oldFilename;
    @Column(name = "new_filename")
    private String newFilename;

    // 图片
    private byte[] img;

    // 内容（合同）
    private String  content;

    // 对应字典类型
    private Integer dictType;

    private String ext;
    private String size;
    private String type;
    private String path;
    private String description;
    /**
     * 是否禁用：1(启用) 0(禁用)
     */
    private Integer flag;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "creator_id")
    private String updatorId;

    @Column(name = "updator_id")
    private String creatorId;
}
