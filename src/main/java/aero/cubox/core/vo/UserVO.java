package aero.cubox.core.vo;

public class UserVO {

    private int id;
    private String login_id;
    private String login_pwd;
    private String user_nm;
    private String contact_no;
    private String dept_nm;
    private String active_yn;
    private String create_at;
    private String update_at;



    private String loginId;
    private String loginPwd;
    private String userNm;
    private String deptNm;
    private String contactNo;
    private String createdAt;
    private String updatedAt;
    private String activeYn;


    private String srchCond;
    private String keyword;

    private int srchPage			= 1;	//조회할 페이지 번호 기본 1페이지
    private int srchCnt				= 10;	//조회할 페이지 수
    private int offset				= 0;
    private int curPage				= 1;	//조회할 페이지 번호 기본 1페이지
    private int curPageUnit			= 10;	//한번에 표시할 페이지 번호 개수




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

    public int getSrchPage() {
        return srchPage;
    }

    public void setSrchPage(int srchPage) {
        this.srchPage = srchPage;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getDept_nm() {
        return dept_nm;
    }

    public void setDept_nm(String dept_nm) {
        this.dept_nm = dept_nm;
    }

    public String getActive_yn() {
        return active_yn;
    }

    public void setActive_yn(String active_yn) {
        this.active_yn = active_yn;
    }


    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getDeptNm() {
        return deptNm;
    }

    public void setDeptNm(String deptNm) {
        this.deptNm = deptNm;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getActiveYn() {
        return activeYn;
    }

    public void setActiveYn(String activeYn) {
        this.activeYn = activeYn;
    }

    public int getSrchCnt() {
        return srchCnt;
    }

    public void setSrchCnt(int srchCnt) {
        this.srchCnt = srchCnt;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getCurPageUnit() {
        return curPageUnit;
    }

    public void setCurPageUnit(int curPageUnit) {
        this.curPageUnit = curPageUnit;
    }

    public String getSrchCond() {
        return srchCond;
    }

    public void setSrchCond(String srchCond) {
        this.srchCond = srchCond;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void autoOffset(){
        int off = (this.srchPage - 1) * this.srchCnt;
        if(off<0) off = 0;
        this.offset = off;
    }

    @Override
    public String toString() {
        return "UserVO [" + "     id=" + id +
                ",     login_id=" + login_id +
                ",     login_pwd=" + login_pwd +
                ",     user_nm=" + user_nm +
                ",     contact_no=" + contact_no +
                ",     dept_nm=" + dept_nm +
                ",     active_yn=" + active_yn +
                ",     create_at=" + create_at +
                ",     update_at=" + update_at +
                ",     srchPage=" + srchPage +
                ",     srchCnt=" + srchCnt +
                ",     offset=" + offset +
                ",     curPage=" + curPage +
                ",     curPageUnit=" + curPageUnit +
                "]";
    }
}
