package com.dv.uni.sys.entity;

import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.entity.SortEntity;
import com.dv.uni.commons.group.UpdateGroup;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
@Table
@Entity
@DynamicInsert
@DynamicUpdate
@Data
@ApiModel("系统配置")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
public class SysConfig implements BaseEntity<String> {
    @Transient
    private List<SortEntity> sortBy;

    @Id
    @Column(length = 32, columnDefinition = "VARCHAR(32) COMMENT 'ID'")
    @ApiModelProperty("ID")
    @Length(max = 32)
    @NotBlank(groups = {UpdateGroup.class})
    private String id;

    @Column(columnDefinition = "DATETIME COMMENT '创建时间'")
    @ApiModelProperty("创建时间")
    private Date createDate;

    @Transient
    @ApiModelProperty("查询专用")
    private Date[] createDate_scope;

    @Column(columnDefinition = "TIMESTAMP COMMENT '更新时间'")
    @ApiModelProperty("更新时间")
    private Date updateDate;

    @Transient
    @ApiModelProperty("查询专用")
    private Date[] updateDate_scope;

    @Column(length = 32, columnDefinition = "VARCHAR(32) COMMENT '创建人'")
    @ApiModelProperty("创建人")
    @Length(max = 32)
    private String createBy;

    @Column(length = 32, columnDefinition = "VARCHAR(32) COMMENT '最后一次更新人'")
    @ApiModelProperty("最后一次更新人")
    @Length(max = 32)
    private String updateBy;

    @Column(columnDefinition = "JSON COMMENT '扩展字段'")
    @ApiModelProperty("扩展字段")
    @Type(type = "json")
    private Map<String, Object> expands;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '作用域'")
    @ApiModelProperty("作用域")
    @Length(max = 255)
    private String scope;

    @Column
    @ApiModelProperty("删除标记")
    @Max(1)
    private Integer deleted = 0;

    @Column(name = "`name`", columnDefinition = "VARCHAR(255) COMMENT '名称'")
    @ApiModelProperty("名称")
    @Length(max = 255)
    private String name;

    @Column(name = "`key`", unique = true, columnDefinition = "VARCHAR(255) COMMENT '键'")
    @Length(max = 255)
    @ApiModelProperty("键")
    private String key;

    @Column(columnDefinition = "TEXT COMMENT '值'", name = "`value`")
    @ApiModelProperty("值")
    private String value;

    @Column(length = 1, columnDefinition = "VARCHAR(1) COMMENT '是否系统'")
    @ApiModelProperty("是否系统")
    @Length(max = 1)
    private String isSys;

    @Column(length = 4, columnDefinition = "VARCHAR(4) COMMENT '序号'")
    @ApiModelProperty("序号")
    @Length(max = 4, min = 4)
    private String sn;

}
