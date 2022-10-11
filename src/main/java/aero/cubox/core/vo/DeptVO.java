package aero.cubox.core.vo;

public class DeptVO {

    private int id;
    private String deptCd;
    private String deptNm;
    private String insttCd;
    private String insttNm;
    private String createdAt;
    private String updatedAt;

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

    public String getInsttCd() {
        return insttCd;
    }

    public void setInsttCd(String insttCd) {
        this.insttCd = insttCd;
    }

    public String getInsttNm() {
        return insttNm;
    }

    public void setInsttNm(String insttNm) {
        this.insttNm = insttNm;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
        return "DeptVO{" +
                "id=" + id +
                ", deptCd='" + deptCd + '\'' +
                ", deptNm='" + deptNm + '\'' +
                ", insttCd='" + insttCd + '\'' +
                ", insttNm='" + insttNm + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", keyword='" + keyword + '\'' +
                ", srchPage=" + srchPage +
                ", srchCnt=" + srchCnt +
                ", offset=" + offset +
                ", curPage=" + curPage +
                ", curPageUnit=" + curPageUnit +
                '}';
    }
}
