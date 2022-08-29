package tk.myabatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.mapper.UserMapper;
import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 汤琛
 * @PROJECT_NAME: MybatisStudy
 * @DESCRIPTION:
 * @DATE: 2022/8/8 15:17
 */
public class UserMapperTest extends BaseMapperTest{

    @Test
    public void testSelectById(){
        SqlSession sqlSession= getSqlSession();
        try {
            UserMapper userMapper= sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = userMapper.selectById(1L);
            Assert.assertNotNull(sysUser);
            Assert.assertEquals("admin",sysUser.getUserName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> users = userMapper.selectAll();
            Assert.assertNotNull(users);
            Assert.assertTrue(users.size()>0);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRolesByUserId(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper= sqlSession.getMapper(UserMapper.class);
            List<SysRole> sysRoles = userMapper.selectRolesByUserId(1L);
            Assert.assertNotNull(sysRoles);
            Assert.assertTrue(sysRoles.size()>0);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = new SysUser();
            sysUser.setUserName("test1");
            sysUser.setUserPassword("123456");
            sysUser.setUserEmail("test@qq.com");
            sysUser.setUserInfo("test info");
            sysUser.setHeadImg(new byte[]{1,2,3});
            sysUser.setCreateTime(new Date());
            int result = userMapper.insert(sysUser);
            Assert.assertEquals(1,result);
            Assert.assertNull(sysUser.getId());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testInsert2(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = new SysUser();
            sysUser.setUserName("test1");
            sysUser.setUserPassword("123456");
            sysUser.setUserEmail("test@qq.com");
            sysUser.setUserInfo("test info");
            sysUser.setHeadImg(new byte[]{1,2,3});
            sysUser.setCreateTime(new Date());
            int result = userMapper.insert2(sysUser);
            Assert.assertEquals(1,result);
            Assert.assertNotNull(sysUser.getId());
            System.out.println(sysUser.getId());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateById(){
        SqlSession sqlsession = getSqlSession();
        try {
            UserMapper userMapper = sqlsession.getMapper(UserMapper.class);
            SysUser sysUser = userMapper.selectById(1L);
            Assert.assertEquals("admin",sysUser.getUserName());
            sysUser.setUserName("admin_test");
            sysUser.setUserEmail("test@qq.com");
            int result = userMapper.updateById(sysUser);
            Assert.assertEquals(1,result);
            sysUser = userMapper.selectById(1L);
            Assert.assertEquals("admin_test",sysUser.getUserName());
        } finally {
            sqlsession.rollback();
            sqlsession.close();
        }
    }

    @Test
    public void testDeleteById(){
        SqlSession sqlSession =getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = userMapper.selectById(1L);
            Assert.assertNotNull(sysUser);
            Assert.assertEquals(1,userMapper.deleteById(1L));
            Assert.assertNull(userMapper.selectById(1L));
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }

//        SysUser user = userMapper.selectById(1001L);
//        Assert.assertNotNull(user);
//        Assert.assertEquals(1,userMapper.deleteById(1001L));
//        Assert.assertNull(userMapper.selectById(1001L));

    }

    @Test
    public void testSelectRolesByUserIdAndRoleEnabled(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysRole> sysRoles = userMapper.selectRolesByUserIdAndRoleEnabled(1L, 1);
            Assert.assertNotNull(sysRoles);
            Assert.assertTrue(sysRoles.size()>0);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByUser(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            SysUser query = new SysUser();
            query.setUserName("ad");
            List<SysUser> users = mapper.selectByUser(query);
            Assert.assertTrue(users.size()>0);
            query = new SysUser();
            query.setUserEmail("test@qq.com");
            users = mapper.selectByUser(query);
            Assert.assertTrue(users.size()>0);
            query = new SysUser();
            query.setUserName("ad");
            query.setUserEmail("test@qq.com");
            users = mapper.selectByUser(query);
            Assert.assertTrue(users.size() == 0);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByMap(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            Map<String,Object> userMap = new HashMap<String,Object>();
            userMap.put("id",1L);
            userMap.put("user_email","test@qq.com");
            userMap.put("user_password","12345678");
            mapper.updateByMap(userMap);
            SysUser sysUser = mapper.selectById(1L);
            Assert.assertEquals("test@qq.com",sysUser.getUserEmail());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void TestSelectUserAndRoleById(){
        SqlSession sql = getSqlSession();
        try {
            UserMapper mapper = sql.getMapper(UserMapper.class);
            SysUser sysUser = mapper.selectUserAndRoleById(1001L);
            Assert.assertNotNull(sysUser);
//            Assert.assertNotNull(sysUser.getSysRole());
        } finally {
            sql.close();
        }
    }

    @Test
    public void TestSelectUserAndRoleByIdSelect(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = mapper.selectUserAndRoleByIdSelect(1001L);
            Assert.assertNotNull(sysUser);
            System.out.println("调用方法：sysUser.getSysRole()");
//            Assert.assertNotNull(sysUser.getSysRole());
        } finally {
            sqlSession.close();
        }
    }

/*    @Test
    public void TestSelectAllUserAndRoles(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> users = mapper.selectAllUserAndRoles();
            System.out.println("用户数："+users.size());
            for (SysUser user : users) {
                System.out.println("用户名："+user.getUserName());
                for (SysRole sysRole : user.getRoleList()) {
                    System.out.println("角色名："+sysRole.getRoleName());
                }
            }
//            Assert.assertNotNull(users);
//            Assert.assertTrue(users.size()>0);
        } finally {
            sqlSession.close();
        }
    }*/

    @Test
    public void testSelectAllUserAndRolesSelect(){
        SqlSession sqlSession = getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        SysUser sysUser = mapper.selectAllUserAndRolesSelect(1L);
        System.out.println("用户名为："+sysUser.getUserName());
        for (SysRole sysRole : sysUser.getRoleList()) {
            System.out.println("角色名为："+sysRole.getRoleName());
            for (SysPrivilege sysPrivilege : sysRole.getPrivilegeList()) {
                System.out.println("权限名为："+sysPrivilege.getPrivilegeName());
            }
        }
    }

    @Test
    public void testSelectUserById(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            SysUser sysUser = new SysUser();
            sysUser.setId(1L);
            mapper.selectUserById(sysUser);
            Assert.assertNotNull(sysUser.getUserName());
            System.out.println("用户名:"+sysUser.getUserName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserPage(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            Map<String,Object> params = new HashMap<>();
            params.put("userName","ad");
            params.put("offset",0);
            params.put("limit",10);
            List<SysUser> users = mapper.selectUserPage(params);
            //此处可以获取到total，且可以获取到最后的结果集。因为该方法通过total出参返回了查询的总数，resultMap设置了最后的结果集
            Long total = (Long) params.get("total");
            System.out.println("总数"+total);
            for (SysUser user : users) {
                System.out.println("用户名："+user.getUserName());
            }
        } finally {
            sqlSession.close();
        }
    }

/*    @Test
    public void testInsertAndDelete(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@qq.com");
            user.setUserInfo("test info");
            user.setHeadImg(new byte[]{1,2,3});
            mapper.insertUserAndRoles(user,"1,2");
            Assert.assertNotNull(user.getId());
            Assert.assertNotNull(user.getCreateTime());
            mapper.deleteUserById(user.getId());
        } finally {
            sqlSession.close();
        }
    }*/
}
