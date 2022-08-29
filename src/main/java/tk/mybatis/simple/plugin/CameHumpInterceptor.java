package tk.mybatis.simple.plugin;


import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.*;

/**
 * @author 汤琛
 * @PROJECT_NAME: MybatisStudy
 * @DESCRIPTION: Mybatis Map类型下划线key转小写驼峰形式
 * @DATE: 2022/8/23 17:24
 */
@Intercepts({
        @Signature(
                type = ResultSetHandler.class,
                method = "handleResultSets",
                args = {Statement.class}
        )
})
public class CameHumpInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> list = (List<Object>) invocation.proceed();
        for (Object o : list) {
            if ( o instanceof Map){
                processMap((Map) o);
            }else{
                break;
            }
        }
        return list;
    }

    /**
     * @description 处理map集合
     * @date 2022/8/23 17:29
     * @param map
     * @throws
     */
    public void processMap(Map<String,Object> map){
        Set<String> keySet = new HashSet<>(map.keySet());
        for (String s : keySet) {
            //将以大写开头的字符串转换为小写，如果包含下划线也会处理为驼峰
            //此处只通过这两个简单的标识来判断是否进行转换
            if ((s.charAt(0) >='A' && s.charAt(0) <='Z') || s.indexOf("_") >=0 ){
                Object value = map.get(s);
                //此处删除的不是set集合的数据，因此不存在安全性问题，不允许在foreach中删除正在遍历的集合元素，可能会出现异常情况。
                map.remove(s);
                map.put(underlineToCamehump(s),value);
            }
        }
    }

    /**
     * @description 忽略key中的_将_后面的首字符转变为大写，同时将首字母大写的字符串转换为小写
     * @date 2022/8/23 17:38
     * @param key
     * @throws
     * @return java.lang.String
     */
    public static String underlineToCamehump(String key){
        //创建字符缓存流，非线程安全
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        //遍历字符串
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            //如果当前字符是_，而且不是首个字符，设置下一个字符大写标记为真
            if (c == '_'){
                if (sb.length() > 0){
                    nextUpperCase = true;
                }
            }else{
                //如果不是_，而且需要大写，则将当前字符转换为大写存入字符缓存流中，设置下一个字符大写标记为假。
                if (nextUpperCase){
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase =false;
                }else{
                    //不需要大写，但是当前字符可能是大写状态需要转换为小写。
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {
//        Interceptor.super.setProperties(properties);
    }
}
