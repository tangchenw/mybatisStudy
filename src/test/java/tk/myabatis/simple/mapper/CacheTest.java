package tk.myabatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.mapper.RoleMapper;
import tk.mybatis.simple.mapper.UserMapper;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.Map;

/**
 * @author 汤琛
 * @PROJECT_NAME: MybatisStudy
 * @DESCRIPTION:
 * @DATE: 2022/8/22 10:20
 */
public class CacheTest extends BaseMapperTest{
/*    @Test
    public void testL1Cache(){
        SqlSession sqlSession = getSqlSession();
        SysUser sysUser = null;
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //第一次查询id=1用户
            sysUser = mapper.selectById(1L);
            //对当前获取的对象进行赋值
            sysUser.setUserName("New Name");
            //第二次查询id=1的用户，发现没有执行查询语句
            SysUser sysUser1 = mapper.selectById(1L);
            //第二次查询得到的对象的用户名和第一次获取的对象的用户名相同
            Assert.assertEquals("New Name",sysUser1.getUserName());
            //而且对象也相同，这就是mybatis的一级缓存在起作用
            Assert.assertEquals(sysUser,sysUser1);
        } finally {
            sqlSession.close();
        }
        //开启新的sql
        System.out.println("开启新的sqlSession");
        sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //同样查询，执行了sql语句
            SysUser sysUser1 = mapper.selectById(1L);
            //判断查询出来的用户名是不是New Name，发现不是，这就证明mybatis缓存只能作用于sqlSession的生命周期
            Assert.assertNotEquals("New Name",sysUser1.getUserName());
            //同样，对象也不相同
            Assert.assertNotEquals(sysUser,sysUser1);
            //执行删除操作，mybatis执行insert,update,delete操作都会清除缓存
            mapper.deleteById(2L);
            //再次执行同样的查询语句，发现执行了sql，证明确实会清除缓存
            SysUser sysUser2 = mapper.selectById(1L);
            Assert.assertNotEquals(sysUser1,sysUser2);
        } finally {
            sqlSession.close();
        }
    }*/

    @Test
    public void testL2Cache(){
        SqlSession sqlSession = getSqlSession();
        SysRole role1 = null;
        try {
            RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            role1 = mapper.selectById(1L);
            role1.setRoleName("New Name");
            SysRole role2 = mapper.selectById(1L);
            Assert.assertEquals("New Name",role2.getRoleName());
            Assert.assertEquals(role1,role2);
        } finally {
            sqlSession.close();
        }
        System.out.println("开启新的SqlSession");
        sqlSession = getSqlSession();
        try {
            RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role2 = mapper.selectById(1L);
            Assert.assertEquals("New Name",role2.getRoleName());
            Assert.assertNotEquals(role1,role2);
            SysRole role3 = mapper.selectById(1L);
            Assert.assertNotEquals(role2,role3);
        } finally {
            sqlSession.close();
        }

    }

/*    @Test
    public void testDirtyData(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = mapper.selectUserAndRoleById(1001L);
            Assert.assertEquals("普通用户",user.getSysRole().getRoleName());
            System.out.println("角色名："+user.getSysRole().getRoleName());
        } finally {
            sqlSession.close();
        }
        sqlSession = getSqlSession();
        try {
            RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
            SysRole role = mapper.selectById(2L);
            role.setRoleName("脏数据");
            mapper.updateRoleById(role);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        System.out.println("开启新的sqlSession");
        sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1001L);
            SysRole role = roleMapper.selectById(2L);
            Assert.assertEquals("普通用户",user.getSysRole().getRoleName());
            Assert.assertEquals("脏数据",role.getRoleName());
            System.out.println("角色名："+user.getSysRole().getRoleName());
            //还原数据
            role.setRoleName("普通用户");
            roleMapper.updateRoleById(role);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }*/

    @Test
    public void testSelectMapById(){
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> map = mapper.selectMapById(1L);
            map.forEach((k,v)->{
                System.out.println(k+"--------->"+v);
            });
        } finally {
            sqlSession.close();
        }
    }
}
