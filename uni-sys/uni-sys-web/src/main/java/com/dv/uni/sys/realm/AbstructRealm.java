package com.dv.uni.sys.realm;

import com.dv.uni.commons.authc.AuthcToken;
import com.dv.uni.commons.realms.Realm;
import com.dv.uni.sys.entity.SysResource;
import com.dv.uni.sys.entity.SysUser;
import com.dv.uni.sys.service.SysResourceService;
import com.dv.uni.sys.service.SysRoleService;
import com.dv.uni.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/9/14 0014
 */
public abstract class AbstructRealm implements Realm {
    @Autowired
    private SysUserService     sysUserService;
    @Autowired
    private SysResourceService sysResourceService;
    @Autowired
    private SysRoleService     sysRoleService;

    @Override
    public Set<String> authorization(AuthcToken token) {
        SysUser sysUser = sysUserService.findById((String) token.principal());
        if ("root".equals(sysUser.getUsername())) {
            return sysResourceService.findAll(new SysResource())
                                     .stream()
                                     .map(s -> {
                                         return s.getCode();
                                     })
                                     .collect(Collectors.toSet());
        }
        Set<String> permissions = new HashSet<>();
        if (sysUser.getResources()!=null&&!sysUser.getResources().isEmpty()){
            permissions.addAll(sysUser.getResources()
                                      .stream()
                                      .map(s -> {
                                          return s.getCode();
                                      })
                                      .collect(Collectors.toSet()));
        }
        if (sysUser.getRoles()!=null&&!sysUser.getRoles().isEmpty()){
            sysUser.getRoles().forEach(sysRole -> {
                if (sysRole.getResources()!=null&&!sysRole.getResources().isEmpty()){
                    permissions.addAll(sysRole.getResources()
                                              .stream()
                                              .map(s -> {
                                                  return s.getCode();
                                              })
                                              .collect(Collectors.toSet()));
                }
            });
        }
        return permissions;
    }
}
