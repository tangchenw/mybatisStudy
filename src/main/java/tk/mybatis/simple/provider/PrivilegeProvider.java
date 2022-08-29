package tk.mybatis.simple.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author 汤琛
 * @PROJECT_NAME: MybatisStudy
 * @DESCRIPTION:
 * @DATE: 2022/8/9 11:02
 */
public class PrivilegeProvider {
    public String selectById(final Long id){
        return new SQL(){
            {
               SELECT("id,privilege_name,privilege_url from sys_privilege where id = #{id}");
            }
        }.toString();
    }
}
