package tk.myabatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.mapper.PrivilegeMapper;
import tk.mybatis.simple.model.SysPrivilege;

/**
 * @author 汤琛
 * @PROJECT_NAME: MybatisStudy
 * @DESCRIPTION:
 * @DATE: 2022/8/9 11:07
 */
public class PrivilegeMapperTest extends BaseMapperTest{
    @Test
    public void testSelectById(){
        SqlSession sqlSession = getSqlSession();
        try {
            PrivilegeMapper mapper = sqlSession.getMapper(PrivilegeMapper.class);
            SysPrivilege sysPrivilege = mapper.selectById(1L);
            Assert.assertNotNull(sysPrivilege);
            Assert.assertEquals("用户管理",sysPrivilege.getPrivilegeName());
        } finally {
            sqlSession.close();
        }
    }
}
