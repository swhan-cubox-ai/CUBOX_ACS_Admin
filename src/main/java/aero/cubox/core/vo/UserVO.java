package aero.cubox.core.vo;

public class UserVO {

    private int id;

    private String login_id;

    private String login_pwd;

    private String user_nm;

    private String create_at;

    private String update_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getLogin_pwd() {
        return login_pwd;
    }

    public void setLogin_pwd(String login_pwd) {
        this.login_pwd = login_pwd;
    }

    public String getUser_nm() {
        return user_nm;
    }

    public void setUser_nm(String user_nm) {
        this.user_nm = user_nm;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    @Override
    public String toString() {
        return "UserVO [" + "     id=" + id +
                ",     login_id=" + login_id +
                ",     login_pwd=" + login_pwd +
                ",     user_nm=" + user_nm +
                ",     create_at=" + create_at +
                ",     update_at=" + update_at +
                "]";
    }
}
