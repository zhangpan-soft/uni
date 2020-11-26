package com.dv.uni.sys.entity;

import com.dv.uni.commons.entity.BaseEntity;
import com.dv.uni.commons.entity.SortEntity;
import com.dv.uni.commons.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
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
 * @Date 2020/9/11 0011
 */
@Data
@Table(name = "sys_user")
@DynamicUpdate
@DynamicInsert
@Entity
@ApiModel("用户")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
public class SysUser implements BaseEntity<String> {
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

    @Column(length = 32, unique = true, columnDefinition = "VARCHAR(32) COMMENT '用户名'")
    @ApiModelProperty("用户名")
    @Length(max = 32)
    private String username;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '密码'")
    @ApiModelProperty("密码")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Length(max = 255)
    private String password;

    @Column(length = 8, columnDefinition = "VARCHAR(8) COMMENT '盐'")
    @ApiModelProperty("盐")
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Length(max = 8)
    private String salt;

    @Column(length = 32, columnDefinition = "VARCHAR(32) COMMENT '昵称'")
    @ApiModelProperty("昵称")
    @Length(max = 32)
    private String nickname;

    @Column(length = 32, columnDefinition = "VARCHAR(32) COMMENT '真实姓名'")
    @ApiModelProperty("真实姓名")
    @Length(max = 32)
    private String realName;

    @Column(length = 16, unique = true, columnDefinition = "VARCHAR(16) COMMENT '手机号'")
    @ApiModelProperty("手机号")
    @Length(max = 16)
    private String phone;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '头像'")
    @ApiModelProperty("头像")
    @Length(max = 255)
    private String avatar;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "sys_user_role_mapping", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<SysRole> roles;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "sys_user_resource_mapping", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "resource_id")})
    private List<SysResource> resources;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<SysUserThird> userThirds;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organizationId")
    private SysOrganization organization;

    @Column(length = 64)
    @ApiModelProperty("email")
    @Length(max = 64)
    private String email;

    @Transient
    @ApiModelProperty("本用户所有权限")
    private List<SysResource> allResources;
}
