package tk.mybatis.simple.plugin;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

/**
 * @author 汤琛
 */
public interface Dialect {
    /**
     * @description 跳过count和分页查询
     * @date 2022/8/24 15:30
     * @param msId  执行的Mybatis方法全名
     * @param parameterObject 方法参数
     * @param rowBounds  分页参数
     * @throws
     * @return boolean  true跳过，返回默认查询结果;false则执行分页查询
     */
    boolean skip(String msId, Object parameterObject, RowBounds rowBounds);

    /**
     * @description 执行分页前，返回true会进行count查询，返回false会继续下面的beforePage判断
     * @date 2022/8/24 15:32
     * @param msId  执行的Mybatis方法全名
     * @param parameterObject   方法参数
     * @param rowBounds 分页参数
     * @throws
     * @return boolean
     */
    boolean beforeCount(String msId,Object parameterObject, RowBounds rowBounds);

    /**
     * @description 生成count查询Sql
     * @date 2022/8/24 15:41
     * @param boundSql boundSql绑定的SQL对象
     * @param parameterObject 方法参数
     * @param rowBounds  分页参数
     * @param cacheKey  count缓存key
     * @throws
     * @return java.lang.String
     */
    String getCountSql(BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey cacheKey);

    /**
     * @description 执行完count查询后
     * @date 2022/8/24 15:46
     * @param count 查询结果总数
     * @param parameterObject 接口参数
     * @param rowBounds 分页查询
     * @throws
     */
    void afterCount(long count, Object parameterObject, RowBounds rowBounds);

    /**
     * @description 执行分页前，返回true会进行分页查询，返回false会返回默认的查询结果
     * @date 2022/8/24 15:47
     * @param msId  执行的Mybatis方法全名
     * @param parameterObject   方法参数
     * @param rowBounds 分页查询
     * @throws
     * @return boolean
     */
    boolean beforePage(String msId, Object parameterObject, RowBounds rowBounds);

    /**
     * @description 生成分页查询sql
     * @date 2022/8/24 15:49
     * @param boundSql  绑定sql对象
     * @param parameterObject   方法参数
     * @param rowBounds 分页参数
     * @param cacheKey  分页缓存key
     * @throws
     * @return java.lang.String
     */
    String getPageSql(BoundSql boundSql, Object parameterObject,RowBounds rowBounds,CacheKey cacheKey);

    /**
     * @description 分页查询后，处理分页结果，拦截器中直接return该方法的返回值
     * @date 2022/8/24 15:50
     * @param pageList  分页查询结果
     * @param parameterObject   方法参数
     * @param rowBounds 分页参数
     * @throws
     * @return java.lang.Object
     */
    Object afterPage(List pageList, Object parameterObject, RowBounds rowBounds);

    /**
     * @description 设置参数
     * @date 2022/8/24 15:51
     * @param properties  插件属性
     * @throws
     */
    void setProperties(Properties properties);
}
