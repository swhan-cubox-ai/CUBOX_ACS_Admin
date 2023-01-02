package aero.cubox.core.vo;

public class EmpVO {

    private int id;
    private String empNm;
    private String empCd;
    private String insttCd;
    private String insttNm;
    private String deptCd;
    private String deptNm;
    private String belongNm;

    private String cardStateTypNm;
    private String expiredDt;
    private String createdAt;
    private String updatedAt;

    private String isExcel;

    private String keyword1;
    private String keyword2;

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

    public String getEmpNm() {
        return empNm;
    }

    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }

    public String getEmpCd() {
        return empCd;
    }

    public void setEmpCd(String empCd) {
        this.empCd = empCd;
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

    public String getBelongNm() {
        return belongNm;
    }

    public void setBelongNm(String belongNm) {
        this.belongNm = belongNm;
    }

    public String getCardStateTypNm() {
        return cardStateTypNm;
    }

    public void setCardStateTypNm(String cardStateTypNm) {
        this.cardStateTypNm = cardStateTypNm;
    }

    public String getExpiredDt() {
        return expiredDt;
    }

    public void setExpiredDt(String expiredDt) {
        this.expiredDt = expiredDt;
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

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
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
        return "EmpVO{" +
                "id=" + id +
                ", empNm='" + empNm + '\'' +
                ", empCd='" + empCd + '\'' +
                ", insttCd='" + insttCd + '\'' +
                ", insttNm='" + insttNm + '\'' +
                ", deptCd='" + deptCd + '\'' +
                ", deptNm='" + deptNm + '\'' +
                ", belongNm='" + belongNm + '\'' +
                ", expiredDt='" + expiredDt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", keyword1='" + keyword1 + '\'' +
                ", keyword2='" + keyword2 + '\'' +
                ", srchPage=" + srchPage +
                ", srchCnt=" + srchCnt +
                ", offset=" + offset +
                ", curPage=" + curPage +
                ", curPageUnit=" + curPageUnit +
                '}';
    }
}
