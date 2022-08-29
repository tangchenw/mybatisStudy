package tk.myabatis.simple.mapper;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.mapper.RoleMapper;
import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.plugin.PageRowBounds;
import tk.mybatis.simple.type.Enabled;

import java.util.List;

/**
 * @author 汤琛
 * @PROJECT_NAME: MybatisStudy
 * @DESCRIPTION:
 * @DATE: 2022/8/9 9:52
 */
public class RoleMapperTest extends BaseMapperTest {
    @Test
    public void testSelectById(){
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = roleMapper.selectById(1L);
            Assert.assertNotNull(sysRole);
            Assert.assertEquals("管理员",sysRole.getRoleName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectById2(){
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = roleMapper.selectById2(1L);
            Assert.assertNotNull(sysRole);
            Assert.assertEquals("管理员",sysRole.getRoleName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> sysRoles = roleMapper.selectAll();
            Assert.assertNotNull(sysRoles);
            Assert.assertTrue(sysRoles.size()>0);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRoleByUserIdChoose(){
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = mapper.selectById(2L);
//            sysRole.setEnabled(0);
            sysRole.setEnabled(Enabled.enabled);
            mapper.updateRoleById(sysRole);
            List<SysRole> roles = mapper.selectRoleByUserIdChoose(1L);
            for (SysRole role : roles) {
                System.out.println("角色名为："+role.getRoleName());
                if (role.getId().equals(1L)){
                    //第一个角色中存在权限信息
                    Assert.assertNotNull(role.getPrivilegeList());
                }else if (role.getId().equals(2L)){
                    //第二个角色的权限为空
                    Assert.assertNull(role.getPrivilegeList());
                    continue;
                }
                for (SysPrivilege sysPrivilege : role.getPrivilegeList()) {
                    System.out.println("权限名为："+sysPrivilege.getPrivilegeName());
                }
            }
        } finally {
            sqlSession.close();
        }

    }

    @Test
    public void testUpdateById(){
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = mapper.selectById(2L);
            Assert.assertEquals(Enabled.enabled,sysRole.getEnabled());
            sysRole.setEnabled(Enabled.disabled);
            mapper.updateRoleById(sysRole);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAllByRowBounds(){
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //查询第一个，使用RowBounds类型时不会查询总数
            RowBounds rowBounds = new RowBounds(0,1);
            List<SysRole> roles = roleMapper.selectAll(rowBounds);
            for (SysRole role : roles) {
                System.out.println("角色名："+role.getRoleName());
            }
            //使用PageRowBounds时，会查询总数
            PageRowBounds pageRowBounds = new PageRowBounds(0,1);
            List<SysRole> roles1 = roleMapper.selectAll(pageRowBounds);
            //获取总数
            System.out.println("查询总数："+pageRowBounds.getTotal());
            for (SysRole role : roles1) {
                System.out.println("角色名："+role.getRoleName());
            }
            //再次查询第二个角色
            pageRowBounds = new PageRowBounds(1,1);
            List<SysRole> roles2 = roleMapper.selectAll(pageRowBounds);
            System.out.println("查询总数："+pageRowBounds.getTotal());
            for (SysRole role : roles2) {
                System.out.println("角色名："+role.getRoleName());
            }
        } finally {
            sqlSession.close();
        }
    }
}
