package tk.mybatis.simple.type;

/**
 * @author 汤琛
 * @PROJECT_NAME: MybatisStudy
 * @DESCRIPTION:
 * @DATE: 2022/8/19 14:52
 */
public enum Enabled {
    //启用
    enabled(1),
    //禁用
    disabled(0);

    private final int value;

    private Enabled(int value){
        this.value = value;
    }

    int getValue(){
        return value;
    }
}
