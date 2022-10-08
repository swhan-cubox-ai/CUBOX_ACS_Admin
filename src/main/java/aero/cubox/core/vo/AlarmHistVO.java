package aero.cubox.core.vo;

public class AlarmHistVO {

    private int id;
    private String evtDt;
    private String doorAlarmTyp;
    private String doorAlarmTypNm;
    private String terminalId;
    private String terminalCd;
    private String terminalTyp;
    private String ipAddr;
    private String modelNm;
    private String mgmtNum;
    private String complexAuthTyp;
    private String complexAuthTypNm;
    private String doorId;
    private String doorNm;
    private String buildingId;
    private String buildingNm;
    private String createdAt;

    private String srchCond;
    private String keyword;

    private String fromDt;
    private String toDt;

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

    public String getEvtDt() {
        return evtDt;
    }

    public void setEvtDt(String evtDt) {
        this.evtDt = evtDt;
    }

    public String getDoorAlarmTyp() {
        return doorAlarmTyp;
    }

    public void setDoorAlarmTyp(String doorAlarmTyp) {
        this.doorAlarmTyp = doorAlarmTyp;
    }

    public String getDoorAlarmTypNm() {
        return doorAlarmTypNm;
    }

    public void setDoorAlarmTypNm(String doorAlarmTypNm) {
        this.doorAlarmTypNm = doorAlarmTypNm;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
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

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getModelNm() {
        return modelNm;
    }

    public void setModelNm(String modelNm) {
        this.modelNm = modelNm;
    }

    public String getMgmtNum() {
        return mgmtNum;
    }

    public void setMgmtNum(String mgmtNum) {
        this.mgmtNum = mgmtNum;
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

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    public String getDoorNm() {
        return doorNm;
    }

    public void setDoorNm(String doorNm) {
        this.doorNm = doorNm;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingNm() {
        return buildingNm;
    }

    public void setBuildingNm(String buildingNm) {
        this.buildingNm = buildingNm;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public String getFromDt() {
        return fromDt;
    }

    public void setFromDt(String fromDt) {
        this.fromDt = fromDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
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
        return "AlarmHistVO{" +
                "id=" + id +
                ", evtDt='" + evtDt + '\'' +
                ", doorAlarmTyp='" + doorAlarmTyp + '\'' +
                ", doorAlarmTypNm='" + doorAlarmTypNm + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", terminalCd='" + terminalCd + '\'' +
                ", terminalTyp='" + terminalTyp + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", modelNm='" + modelNm + '\'' +
                ", mgmtNum='" + mgmtNum + '\'' +
                ", complexAuthTyp='" + complexAuthTyp + '\'' +
                ", complexAuthTypNm='" + complexAuthTypNm + '\'' +
                ", doorId='" + doorId + '\'' +
                ", doorNm='" + doorNm + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", buildingNm='" + buildingNm + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", srchCond='" + srchCond + '\'' +
                ", keyword='" + keyword + '\'' +
                ", fromDt='" + fromDt + '\'' +
                ", toDt='" + toDt + '\'' +
                ", srchPage=" + srchPage +
                ", srchCnt=" + srchCnt +
                ", offset=" + offset +
                ", curPage=" + curPage +
                ", curPageUnit=" + curPageUnit +
                '}';
    }
}
