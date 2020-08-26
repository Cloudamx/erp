package com.Cloudam.sys.realm;

import com.Cloudam.sys.entity.Permission;
import com.Cloudam.sys.entity.User;
import com.Cloudam.sys.service.PermissionService;
import com.Cloudam.sys.service.RoleService;
import com.Cloudam.sys.service.UserService;
import com.Cloudam.bus.common.utils.SystemConstant;
import com.Cloudam.sys.vo.LoginUserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Cloudam
 * @Description:
 * @Date:10:40星期六
 */
public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //获取当前主体
        LoginUserVo userVo = (LoginUserVo) principalCollection.getPrimaryPrincipal();
        //获取当前用户拥有的权限
        Set<String> permissions = userVo.getPermissions();
        //判断是否为超级管理员
        if(userVo.getUser().getType() == SystemConstant.SUPERUSER){
            simpleAuthorizationInfo.addStringPermission("*:*");
        }else{
            if(permissions != null && permissions.size()>0){
                simpleAuthorizationInfo.addStringPermissions(permissions);
            }
        }


        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取当前登录主体
        String userName = (String) authenticationToken.getPrincipal();
        try {
            //根据用户名查询用户信息的方法
            User user = userService.findUserByName(userName);
            //对象不为空
            if(user!=null){
                //创建当前登录用户对象
                //创建登录用户对象，传入用户信息，角色列表，权限列表
                LoginUserVo loginUserVo = new LoginUserVo(user,null,null);
                //开始
                QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("type",SystemConstant.TYPE_PERMISSION);

                //根据当前用户id查询该用户拥有的角色列表
                Set<Integer> currentUserRoleIds = userService.findUserRoleByUserId(user.getId());
                //保存菜单集合  需要去重就选择set
                Set<Integer> pids = new HashSet<>();
                //循环遍历  当前用户拥有的角色列表
                for (Integer roleId : currentUserRoleIds) {
                    //4.根据角色Id查询每个角色下拥有的权限菜单
                    Set<Integer> permissionIds = roleService.finRolePermissionByRoleId(roleId);
                    //将查询出来的权限id放入集合中
                    pids.addAll(permissionIds);
                }
                //创建集合保存权限
                List<Permission> list = new ArrayList<>();
                //判断pids集合是否有值
                if(pids != null && pids.size()>0){
                    queryWrapper.in("id",pids);
                    //执行查询
                    list = permissionService.list(queryWrapper);
                }
                //查询权限编码
                Set<String> perCodes = new HashSet<>();

                for (Permission permission : list) {
                    perCodes.add(permission.getPercode());
                }

                loginUserVo.setPermissions(perCodes);


                //创建盐值(以用户名作为盐值)
                ByteSource salt = ByteSource.Util.bytes(user.getSalt());
                //创建身份验证对象
                //参数1：当前登录对象  参数2：密码  参数3：盐值 参数4：域名
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginUserVo,user.getLoginpwd(),salt,getName());
                return info;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //验证失败
        return null;
    }
}

