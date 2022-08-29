package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 汤琛
 * @PROJECT_NAME: MybatisStudy
 * @DESCRIPTION:
 * @DATE: 2022/8/8 11:34
 */
public interface UserMapper {

    SysUser selectById(Long id);

    List<SysUser> selectAll();

    List<SysRole> selectRolesByUserId(Long userId);

    int insert(SysUser sysUser);

    int insert2(SysUser sysUser);

    int insert3(SysUser sysUser);

    int updateById(SysUser sysUser);

    int deleteById(Long id);

    List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId") Long userId,@Param("enabled") Integer enabled);

    /**
     * @description xml使用参数时，不能直接使用user,role;而是要从#{user.id},#{role.enabled}这样的方式取出指定属性的值。
     * @date 2022/8/8 18:06
     * @param sysUser 系统用户对象
     * @param sysRole 权限对象
     * @throws
     * @return java.util.List<tk.mybatis.simple.model.SysRole>
     */
    List<SysRole> selectRolesByUserIdAndRole(
            @Param("user")SysUser sysUser,
            @Param("role")SysRole sysRole
    );

    List<SysUser> selectByUser(SysUser sysUser);

    int insertList(List<SysUser> userList);



    int updateByMap(Map<String,Object> map);

    SysUser selectUserAndRoleById(Long id);

    SysUser selectUserAndRoleById2(Long id);

    SysUser selectUserAndRoleByIdSelect(Long id);

    List<SysUser> selectAllUserAndRoles();

    SysUser selectAllUserAndRolesSelect(Long id);

    void selectUserById(SysUser user);

    List<SysUser> selectUserPage(Map<String,Object> params);

    void insertUserAndRoles(@Param("user")SysUser user,@Param("roleIds")String roleIds);

    int deleteUserById(Long id);

    Map<String,Object> selectMapById(Long id);
}
