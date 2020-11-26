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
 * @Date 2020/9/17 0017
 */
@Table
@Entity
@DynamicInsert
@DynamicUpdate
@Data
@ApiModel("字典数据表")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
public class SysDicData implements BaseEntity<String> {
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
    private String createBy;

    @Column(length = 32, columnDefinition = "VARCHAR(32) COMMENT '最后一次更新人'")
    @ApiModelProperty("最后一次更新人")
    private String updateBy;

    @Column(columnDefinition = "JSON COMMENT '扩展字段'")
    @ApiModelProperty("扩展字段")
    @Type(type = "json")
    private Map<String, Object> expands;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '作用域'")
    @ApiModelProperty("作用域")
    private String scope;

    @Column
    @ApiModelProperty("删除标记")
    @Max(1)
    private Integer deleted = 0;

    @ApiModelProperty("类型id")
    @Column(length = 32, columnDefinition = "VARCHAR(32) COMMENT '类型id'")
    @Length(max = 32)
    private String dicDataId;

    @ApiModelProperty("字典编码")
    @Column(columnDefinition = "VARCHAR(255) COMMENT '字典编码'")
    @Length(max = 255)
    private String dictCode;

    @ApiModelProperty("字典类型")
    @Column(columnDefinition = "VARCHAR(255) COMMENT '字典类型'")
    @Length(max = 255)
    private String dictType;

    @ApiModelProperty("字典名称")
    @Column(columnDefinition = "VARCHAR(255) COMMENT '字典名称'")
    @Length(max = 255)
    private String dictName;

    @ApiModelProperty("系统内置（1是 0否）")
    @Column(columnDefinition = "VARCHAR(1) COMMENT '是否系统'")
    @Length(max = 1)
    private String isSys;

    @ApiModelProperty("字典描述")
    @Column(columnDefinition = "VARCHAR(255) COMMENT '字典描述'")
    @Length(max = 255)
    private String description;

    @ApiModelProperty("css样式（如：color:red)")
    @Column(columnDefinition = "VARCHAR(255) COMMENT 'css样式（如：color:red)'")
    @Length(max = 255)
    private String cssStyle;

    @ApiModelProperty("css类名（如：red）")
    @Column(columnDefinition = "VARCHAR(255) COMMENT 'css类名（如：red）'")
    @Length(max = 255)
    private String cssClass;

    @ApiModelProperty("备注信息")
    @Column(columnDefinition = "VARCHAR(255) COMMENT '备注信息'")
    @Length(max = 255)
    private String remarks;
}
