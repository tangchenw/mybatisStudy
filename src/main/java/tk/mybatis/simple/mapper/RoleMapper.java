package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.simple.model.SysRole;

import java.util.List;

//@CacheNamespace(
//        eviction = FifoCache.class,
//        flushInterval = 60000,
//        size = 512,
//        readWrite = true
//)
@CacheNamespaceRef(RoleMapper.class)
public interface RoleMapper {

    @Select({"select id,role_name roleName,enabled,create_by createBy,create_time createTime from sys_role where id = #{id}"})
    SysRole selectById(Long id);


    /**
     * @description @Results可以用来实现关系的映射，如果需要在其它地方复用这个映射关系集，mybatis3.3.1之后版本支持通过id属性进行复用
     * @date 2022/8/9 9:44
     * @param id
     * @throws
     * @return tk.mybatis.simple.model.SysRole
     */
    @Results(id = "roleResultMap",value = {
            @Result(property = "id",column = "id",id = true),
            @Result(property = "roleName",column = "role_name"),
            @Result(property = "enabled",column = "enabled"),
            @Result(property = "createBy",column = "create_by"),
            @Result(property = "createTime",column = "create_time"),
    })
    @Select({"select id,role_name,enabled,create_by,create_time from sys_role where id = #{id}"})
    SysRole selectById2(Long id);


    /**
     * @description 映射关系集的复用。如果映射关系集是使用xml来配置的话，则注解的参数值就是xml里面映射关系集resultMap的id属性值。
     * @date 2022/8/9 9:47
     * @param
     * @throws
     * @return java.util.List<tk.mybatis.simple.model.SysRole>
     */
    @ResultMap("roleResultMap")
    @Select("select * from sys_role")
    List<SysRole> selectAll();

    /**
     * @description 分页查询所有
     * @date 2022/8/25 9:58
     * @param rowBounds 分页参数
     * @throws
     * @return java.util.List<tk.mybatis.simple.model.SysRole>
     */
    List<SysRole> selectAll(RowBounds rowBounds);


    @Insert({"insert into sys_role(id,role_name,enabled,create_by,create_time) values(#{id},#{roleName},#{enabled}," +
            "#{createBy},#{createTime,jdbcType=TIMESTAMP})"})
    int insert(SysRole sysRole);

    /**
     * @description 需要返回数据库自增主键值。
     * @date 2022/8/9 10:31
     * @param sysRole
     * @throws
     * @return int
     */
    @Insert({"insert into sys_role(id,role_name,enabled,create_by,create_time) values(#{id},#{roleName},#{enabled}," +
            "#{createBy},#{createTime,jdbcType=TIMESTAMP})"})
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insert2(SysRole sysRole);

    /**
     * @description 非自增主键值的主键返回方式
     * @date 2022/8/9 10:33
     * @param sysRole
     * @throws
     * @return int
     */
    @Insert({"insert into sys_role(id,role_name,enabled,create_by,create_time) values(#{id},#{roleName},#{enabled}," +
            "#{createBy},#{createTime,jdbcType=TIMESTAMP})"})
    @SelectKey(statement = "select LAST_INSERT_ID()",keyProperty = "id",resultType = Long.class,before = false)
    int insert3(SysRole sysRole);

    List<SysRole> selectRoleByUserIdChoose(Long userId);

    int updateRoleById(SysRole sysRole);
}
