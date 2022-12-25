package aero.cubox.core.vo;

public class AuthVO {

    private int id;
    private String authNm;
    private String authTyp;
    private String authTypNm;
    private String deptAuthYn;
    private String deptCd;
    private String deptNm;
    private String authEmpCnt;
    private String useYn;
    private String createdAt;
    private String updatedAt;

    private String isExcel;
    private String keyword;
    private String srchCond;

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

    public String getAuthNm() {
        return authNm;
    }

    public void setAuthNm(String authNm) {
        this.authNm = authNm;
    }

    public String getAuthTyp() {
        return authTyp;
    }

    public void setAuthTyp(String authTyp) {
        this.authTyp = authTyp;
    }

    public String getAuthTypNm() {
        return authTypNm;
    }

    public void setAuthTypNm(String authTypNm) {
        this.authTypNm = authTypNm;
    }

    public String getDeptAuthYn() {
        return deptAuthYn;
    }

    public void setDeptAuthYn(String deptAuthYn) {
        this.deptAuthYn = deptAuthYn;
    }

    public String getDeptCd() {
        return deptCd;
    }

    public void setDeptCd(String deptCd) {
        this.deptCd = deptCd;
    }

    public String getDeptNm() {
        return deptNm;
    }

    public void setDeptNm(String deptNm) {
        this.deptNm = deptNm;
    }

    public String getAuthEmpCnt() {
        return authEmpCnt;
    }

    public void setAuthEmpCnt(String authEmpCnt) {
        this.authEmpCnt = authEmpCnt;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
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

    public String getIsExcel() {
        return isExcel;
    }

    public void setIsExcel(String isExcel) {
        this.isExcel = isExcel;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSrchCond() {
        return srchCond;
    }

    public void setSrchCond(String srchCond) {
        this.srchCond = srchCond;
    }

    public int getSrchPage() {
        return srchPage;
    }

    public void setSrchPage(int srchPage) {
        this.srchPage = srchPage;
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

    public void autoOffset(){
        int off = (this.srchPage - 1) * this.srchCnt;
        if(off<0) off = 0;
        this.offset = off;
    }

    @Override
    public String toString() {
        return "AuthVO{" +
                "id=" + id +
                ", authNm='" + authNm + '\'' +
                ", authTyp='" + authTyp + '\'' +
                ", authTypNm='" + authTypNm + '\'' +
                ", deptAuthYn='" + deptAuthYn + '\'' +
                ", deptCd='" + deptCd + '\'' +
                ", authEmpCnt='" + authEmpCnt + '\'' +
                ", useYn='" + useYn + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", keyword='" + keyword + '\'' +
                ", srchCond='" + srchCond + '\'' +
                ", srchPage=" + srchPage +
                ", srchCnt=" + srchCnt +
                ", offset=" + offset +
                ", curPage=" + curPage +
                ", curPageUnit=" + curPageUnit +
                '}';
    }
}
