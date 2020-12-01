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

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/11/26 0026
 */
@Table
@Entity
@DynamicInsert
@DynamicUpdate
@Data
@ApiModel("商品")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
public class UniCommodity implements BaseEntity<String> {
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

    @Column(length = 64)
    @Length(max = 64)
    @ApiModelProperty("商品名称")
    private String name;

    @Column(columnDefinition = "DECIMAL(22,8)")
    @ApiModelProperty("最高价")
    private BigDecimal maxPrice;

    @Column(columnDefinition = "DECIMAL(22,8)")
    @ApiModelProperty("最低价")
    private BigDecimal minPrice;

    @Type(type = "json")
    @Column(columnDefinition = "JSON COMMENT '封面图片'")
    @ApiModelProperty("封面图片")
    private List<String> coverPictures;

    @Type(type = "json")
    @Column(columnDefinition = "JSON COMMENT '图片'")
    @ApiModelProperty("图片")
    private List<String> pictures;

    @Type(type = "json")
    @Column(columnDefinition = "JSON COMMENT '封面视频'")
    @ApiModelProperty("封面视频")
    private List<String> coverVideos;

    @Type(type = "json")
    @Column(columnDefinition = "JSON COMMENT '视频'")
    @ApiModelProperty("视频")
    private List<String> videos;

    @Column(columnDefinition = "VARCHAR(3000) COMMENT '简单说明'")
    @ApiModelProperty("简单说明")
    @Length(max = 3000)
    private String introduction;

    @Column(columnDefinition = "TEXT COMMENT '明细'")
    @ApiModelProperty("明细")
    private String detail;

    @ManyToMany
    @JoinTable(name = "uni_commodity_brand_mapping",joinColumns = @JoinColumn(name = "commodityId"),inverseJoinColumns = @JoinColumn(name = "brandId"))
    private List<UniBrand> brands;

    @ManyToMany
    @JoinTable(name = "uni_commodity_category_mapping",joinColumns = @JoinColumn(name = "commodityId"),inverseJoinColumns = @JoinColumn(name = "categoryId"))
    private List<UniCategory> categories;

    @ManyToMany
    @JoinTable(name = "uni_commodity_group_mapping",joinColumns = @JoinColumn(name = "commodityId"),inverseJoinColumns = @JoinColumn(name = "groupId"))
    private List<UniGroup> groups;

    @OneToMany(mappedBy = "commodityId")
    private List<UniLabel> labels;

    @ManyToMany
    @JoinTable(name = "uni_commodity_usp_mapping",joinColumns = @JoinColumn(name = "commodityId"),inverseJoinColumns = @JoinColumn(name = "uspId"))
    private List<UniUsp> usps;
}
