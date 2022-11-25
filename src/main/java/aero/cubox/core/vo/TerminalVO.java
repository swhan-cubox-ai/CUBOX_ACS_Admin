package aero.cubox.core.vo;

public class TerminalVO {

    private int id;
    private String buildingNm;
    private String areaNm;
    private String floorNm;
    private String doorNm;
    private String terminalCd;
    private String terminalTyp;
    private String modelNm;
    private String mgmtNum;
    private String terminalTypNm;
    private String ipAddr;
    private String complexAuthTyp;
    private String complexAuthTypNm;
    private String faceAuthTyp;
    private String faceAuthTypNm;
    private int blackListCnt;
    private int whiteListCnt;
    private String createdAt;
    private String useYn;

    private String isExcel;

    private String srchCond1;
    private String srchCond2;
    private String keyword;

    private int srchPage			= 1;	//조회할 페이지 번호 기본 1페이지
    private int srchCnt				= 10;	//조회할 페이지 수
    private int offset				= 0;
    private int curPage				= 1;	//조회할 페이지 번호 기본 1페이지
    private int curPageUnit			= 10;	//한번에 표시할 페이지 번호 개수





    public String getSrchCond1() {return srchCond1;}
    public void setSrchCond1(String srchCond1) { this.srchCond1 = srchCond1;}
    public String getSrchCond2() { return srchCond2;}
    public void setSrchCond2(String srchCond2) { this.srchCond2 = srchCond2;}

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuildingNm() {
        return buildingNm;
    }

    public void setBuildingNm(String buildingNm) {
        this.buildingNm = buildingNm;
    }

    public String getAreaNm() {
        return areaNm;
    }

    public void setAreaNm(String areaNm) {
        this.areaNm = areaNm;
    }

    public String getFloorNm() {
        return floorNm;
    }

    public void setFloorNm(String floorNm) {
        this.floorNm = floorNm;
    }

    public String getDoorNm() {
        return doorNm;
    }

    public void setDoorNm(String doorNm) {
        this.doorNm = doorNm;
    }

    public String getTerminalCd() {
        return terminalCd;
    }

    public void setTerminalCd(String terminalCd) {
        this.terminalCd = terminalCd;
    }

    public String getTerminalTyp() {
        return terminalTyp;
    }

    public void setTerminalTyp(String terminalTyp) {
        this.terminalTyp = terminalTyp;
    }

    public String getTerminalTypNm() {
        return terminalTypNm;
    }

    public void setTerminalTypNm(String terminalTypNm) { this.terminalTypNm = terminalTypNm; }

    public String getModelNm() { return modelNm; }

    public void setModelNm(String modelNm) { this.modelNm = modelNm; }

    public String getMgmtNum() { return mgmtNum; }

    public void setMgmtNum(String mgmtNum) { this.mgmtNum = mgmtNum; }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getComplexAuthTyp() {
        return complexAuthTyp;
    }

    public void setComplexAuthTyp(String complexAuthTyp) {
        this.complexAuthTyp = complexAuthTyp;
    }

    public String getComplexAuthTypNm() {
        return complexAuthTypNm;
    }

    public void setComplexAuthTypNm(String complexAuthTypNm) {
        this.complexAuthTypNm = complexAuthTypNm;
    }

    public String getFaceAuthTyp() {
        return faceAuthTyp;
    }

    public void setFaceAuthTyp(String faceAuthTyp) {
        this.faceAuthTyp = faceAuthTyp;
    }

    public String getFaceAuthTypNm() {
        return faceAuthTypNm;
    }

    public void setFaceAuthTypNm(String faceAuthTypNm) {
        this.faceAuthTypNm = faceAuthTypNm;
    }

    public int getBlackListCnt() {
        return blackListCnt;
    }

    public void setBlackListCnt(int blackListCnt) {
        this.blackListCnt = blackListCnt;
    }

    public int getWhiteListCnt() {
        return whiteListCnt;
    }

    public void setWhiteListCnt(int whiteListCnt) {
        this.whiteListCnt = whiteListCnt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getIsExcel() {
        return isExcel;
    }

    public void setIsExcel(String isExcel) {
        this.isExcel = isExcel;
    }

    public void autoOffset(){
        int off = (this.srchPage - 1) * this.srchCnt;
        if(off<0) off = 0;
        this.offset = off;
    }

    @Override
    public String toString() {
        return "TerminalVO{" +
                "id=" + id +
                ", buildingNm='" + buildingNm + '\'' +
                ", areaNm='" + areaNm + '\'' +
                ", floorNm='" + floorNm + '\'' +
                ", doorNm='" + doorNm + '\'' +
                ", terminalCd='" + terminalCd + '\'' +
                ", terminalTyp='" + terminalTyp + '\'' +
                ", modelNm='" + modelNm + '\'' +
                ", mgmtNum='" + mgmtNum + '\'' +
                ", terminalTypNm='" + terminalTypNm + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", complexAuthTyp='" + complexAuthTyp + '\'' +
                ", complexAuthTypNm='" + complexAuthTypNm + '\'' +
                ", faceAuthTyp='" + faceAuthTyp + '\'' +
                ", faceAuthTypNm='" + faceAuthTypNm + '\'' +
                ", blackListCnt=" + blackListCnt +
                ", whiteListCnt=" + whiteListCnt +
                ", createdAt='" + createdAt + '\'' +
                ", useYn='" + useYn + '\'' +
                ", srchCond1='" + srchCond1 + '\'' +
                ", srchCond2='" + srchCond2 + '\'' +
                ", keyword='" + keyword + '\'' +
                ", srchPage=" + srchPage +
                ", srchCnt=" + srchCnt +
                ", offset=" + offset +
                ", curPage=" + curPage +
                ", curPageUnit=" + curPageUnit +
                '}';
    }
}
