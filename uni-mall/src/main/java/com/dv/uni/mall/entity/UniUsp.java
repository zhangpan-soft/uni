package com.dv.uni.mall.entity;

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
 * @Date 2020/9/15 0015
 */
@Table
@Entity
@DynamicInsert
@DynamicUpdate
@Data
@ApiModel("卖点")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
public class UniUsp implements BaseEntity<String> {
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

    @Column(length = 32, columnDefinition = "VARCHAR(32) COMMENT '名称'")
    @ApiModelProperty("名称")
    @Length(max = 32)
    private String name;

    @Column(columnDefinition = "VARCHAR(255) COMMENT 'url'")
    @ApiModelProperty("url")
    @Length(max = 255)
    private String url;

    @Column(length = 32, columnDefinition = "VARCHAR(32) COMMENT '父级id'")
    @ApiModelProperty("父级id")
    @Length(max = 32)
    private String parentId;

    @ApiModelProperty("序号")
    @Column(length = 4, columnDefinition = "VARCHAR(4) COMMENT '序号'")
    @Length(max = 4)
    private String sn;

    @ApiModelProperty("类型")
    @Column(length = 4, columnDefinition = "VARCHAR(4) COMMENT '类型'")
    @Length(max = 4)
    private String type;

    @OneToMany(mappedBy = "parentId")
    private List<UniUsp> children;

}
