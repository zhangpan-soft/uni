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
@DynamicUpdate
@DynamicInsert
@Data
@ApiModel("用户第三方信息")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
public class SysUserThird implements BaseEntity<String> {
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

    @Column(length = 32, columnDefinition = "VARCHAR(32) COMMENT '第三方类型,WX:微信,ALI-PAY:支付宝,ALI-TB:淘宝'")
    @ApiModelProperty("第三方类型,WX:微信,ALI-PAY:支付宝,ALI-TB:淘宝")
    @Length(max = 32)
    private String type;

    @Column(columnDefinition = "VARCHAR(255) COMMENT 'openId'",unique = true)
    @ApiModelProperty
    @Length(max = 255)
    private String openId;

    @Column(columnDefinition = "VARCHAR(255) COMMENT 'unionId'")
    @ApiModelProperty
    @Length(max = 255)
    private String unionId;

    @Column(columnDefinition = "VARCHAR(32) COMMENT '用户id'")
    @ApiModelProperty("用户id")
    @Length(max = 32)
    private String userId;


}
