package aero.cubox.core.vo;

import java.sql.Blob;

public class EntHistVO {

    private int id;
    private String evtDt;
    private String entEvtTyp;
    private String entEvtTypNm;
    private String terminalCd;
    private String modelNm;
    private String mgmtNum;
    private String ipAddr;
    private String complexAuthTyp;
    private String complexAuthTypNm;
    private String doorNm;
    private String buildingNm;
    private String empNm;
    private String empNo;
    private String deptCd;
    private String deptNm;
    private String belongNm;


    private String srchCond1;
    private String srchCond2;
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

    public String getEntEvtTyp() {
        return entEvtTyp;
    }

    public String getEntEvtTypNm() {
        return entEvtTypNm;
    }

    public void setEntEvtTypNm(String entEvtTypNm) {
        this.entEvtTypNm = entEvtTypNm;
    }

    public void setEntEvtTyp(String entEvtTyp) {
        this.entEvtTyp = entEvtTyp;
    }

    public String getTerminalCd() {
        return terminalCd;
    }

    public void setTerminalCd(String terminalCd) {
        this.terminalCd = terminalCd;
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

    public String getDoorNm() {
        return doorNm;
    }

    public void setDoorNm(String doorNm) {
        this.doorNm = doorNm;
    }

    public String getBuildingNm() {
        return buildingNm;
    }

    public void setBuildingNm(String buildingNm) {
        this.buildingNm = buildingNm;
    }

    public String getEmpNm() {
        return empNm;
    }

    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
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


    public String getSrchCond1() {
        return srchCond1;
    }

    public void setSrchCond1(String srchCond1) {
        this.srchCond1 = srchCond1;
    }

    public String getSrchCond2() {
        return srchCond2;
    }

    public void setSrchCond2(String srchCond2) {
        this.srchCond2 = srchCond2;
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
        return "EntHistVO{" +
                "id=" + id +
                ", evtDt='" + evtDt + '\'' +
                ", entEvtTyp='" + entEvtTyp + '\'' +
                ", entEvtTypNm='" + entEvtTypNm + '\'' +
                ", terminalCd='" + terminalCd + '\'' +
                ", modelNm='" + modelNm + '\'' +
                ", mgmtNum='" + mgmtNum + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", complexAuthTyp='" + complexAuthTyp + '\'' +
                ", doorNm='" + doorNm + '\'' +
                ", buildingNm='" + buildingNm + '\'' +
                ", empNm='" + empNm + '\'' +
                ", empNo='" + empNo + '\'' +
                ", deptCd='" + deptCd + '\'' +
                ", deptNm='" + deptNm + '\'' +
                ", belongNm='" + belongNm + '\'' +
                ", srchCond1='" + srchCond1 + '\'' +
                ", srchCond2='" + srchCond2 + '\'' +
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
