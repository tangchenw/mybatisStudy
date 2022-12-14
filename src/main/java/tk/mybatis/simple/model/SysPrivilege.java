package tk.mybatis.simple.model;

/**
 * @author 汤琛
 * @PROJECT_NAME: MybatisStudy
 * @DESCRIPTION:
 * @DATE: 2022/8/8 11:25
 */
public class SysPrivilege {

    private Long id;

    private String privilegeName;

    private String privilegeUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getPrivilegeUrl() {
        return privilegeUrl;
    }

    public void setPrivilegeUrl(String privilegeUrl) {
        this.privilegeUrl = privilegeUrl;
    }
}
