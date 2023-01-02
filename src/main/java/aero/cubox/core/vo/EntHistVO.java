package aero.cubox.core.vo;

import java.sql.Blob;
import java.sql.Timestamp;

public class EntHistVO {

    private int id;
    private Timestamp evtDt;
    private String evtDtStr;
    private String entEvtTyp;
    private String entEvtTypNm;
    private String terminalCd;
    private String empCd;
    private String empNm;
    private Integer faceId;
    private String cardNo;
    private String cardClassTyp;
    private String cardClassTypNm;
    private String cardStateTyp;
    private String cardStateTypNm;
    private String cardTagTyp;
    private String cardTagTypNm;

    private Timestamp begDt;
    private Timestamp endDt;
    private String authWayTyp;
    private String authWayTypNm;
    private Float matchScore;
    private Float faceThreshold;
    private Timestamp captureAt;
    private Timestamp tagAt;
    private String tagCardNo;
    private String tagEmpCd;
    private Double temper;
    private Float maskConfidence;
    private String terminalTyp;
    private String doorCd;
    private String doorNm;
    private String buildingCd;
    private String buildingNm;
    private String deptCd;
    private String deptNm;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String entFaceImg;


    private String srchCond1;
    private String srchCond2;
    private String keyword;

    private String fromDt;
    private String toDt;

    private String[] srchDeptArray;

    private String deptArray;

    private String isExcel;

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

    public Timestamp getEvtDt() {
        return evtDt;
    }

    public void setEvtDt(Timestamp evtDt) {
        this.evtDt = evtDt;
    }

    public String getCardClassTypNm() {
        return cardClassTypNm;
    }

    public void setCardClassTypNm(String cardClassTypNm) {
        this.cardClassTypNm = cardClassTypNm;
    }

    public String getCardStateTypNm() {
        return cardStateTypNm;
    }

    public void setCardStateTypNm(String cardStateTypNm) {
        this.cardStateTypNm = cardStateTypNm;
    }

    public String getCardTagTypNm() {
        return cardTagTypNm;
    }

    public void setCardTagTypNm(String cardTagTypNm) {
        this.cardTagTypNm = cardTagTypNm;
    }

    public String getAuthWayTypNm() {
        return authWayTypNm;
    }

    public void setAuthWayTypNm(String authWayTypNm) {
        this.authWayTypNm = authWayTypNm;
    }

    public String getEntEvtTyp() {
        return entEvtTyp;
    }

    public void setEntEvtTyp(String entEvtTyp) {
        this.entEvtTyp = entEvtTyp;
    }

    public String getEntEvtTypNm() {
        return entEvtTypNm;
    }

    public void setEntEvtTypNm(String entEvtTypNm) {
        this.entEvtTypNm = entEvtTypNm;
    }

    public String getTerminalCd() {
        return terminalCd;
    }

    public void setTerminalCd(String terminalCd) {
        this.terminalCd = terminalCd;
    }

    public String getEmpCd() {
        return empCd;
    }

    public void setEmpCd(String empCd) {
        this.empCd = empCd;
    }

    public String getEmpNm() {
        return empNm;
    }

    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }

    public Integer getFaceId() {
        return faceId;
    }

    public void setFaceId(Integer faceId) {
        this.faceId = faceId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardClassTyp() {
        return cardClassTyp;
    }

    public void setCardClassTyp(String cardClassTyp) {
        this.cardClassTyp = cardClassTyp;
    }

    public String getCardStateTyp() {
        return cardStateTyp;
    }

    public void setCardStateTyp(String cardStateTyp) {
        this.cardStateTyp = cardStateTyp;
    }

    public String getCardTagTyp() {
        return cardTagTyp;
    }

    public void setCardTagTyp(String cardTagTyp) {
        this.cardTagTyp = cardTagTyp;
    }

    public Timestamp getBegDt() {
        return begDt;
    }

    public void setBegDt(Timestamp begDt) {
        this.begDt = begDt;
    }

    public Timestamp getEndDt() {
        return endDt;
    }

    public void setEndDt(Timestamp endDt) {
        this.endDt = endDt;
    }

    public String getAuthWayTyp() {
        return authWayTyp;
    }

    public void setAuthWayTyp(String authWayTyp) {
        this.authWayTyp = authWayTyp;
    }

    public Float getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(Float matchScore) {
        this.matchScore = matchScore;
    }

    public Float getFaceThreshold() {
        return faceThreshold;
    }

    public void setFaceThreshold(Float faceThreshold) {
        this.faceThreshold = faceThreshold;
    }

    public Timestamp getCaptureAt() {
        return captureAt;
    }

    public void setCaptureAt(Timestamp captureAt) {
        this.captureAt = captureAt;
    }

    public Timestamp getTagAt() {
        return tagAt;
    }

    public void setTagAt(Timestamp tagAt) {
        this.tagAt = tagAt;
    }

    public String getTagCardNo() {
        return tagCardNo;
    }

    public void setTagCardNo(String tagCardNo) {
        this.tagCardNo = tagCardNo;
    }

    public String getTagEmpCd() {
        return tagEmpCd;
    }

    public void setTagEmpCd(String tagEmpCd) {
        this.tagEmpCd = tagEmpCd;
    }

    public Double getTemper() {
        return temper;
    }

    public void setTemper(Double temper) {
        this.temper = temper;
    }

    public Float getMaskConfidence() {
        return maskConfidence;
    }

    public void setMaskConfidence(Float maskConfidence) {
        this.maskConfidence = maskConfidence;
    }

    public String getTerminalTyp() {
        return terminalTyp;
    }

    public void setTerminalTyp(String terminalTyp) {
        this.terminalTyp = terminalTyp;
    }

    public String getDoorCd() {
        return doorCd;
    }

    public void setDoorCd(String doorCd) {
        this.doorCd = doorCd;
    }

    public String getDoorNm() {
        return doorNm;
    }

    public void setDoorNm(String doorNm) {
        this.doorNm = doorNm;
    }

    public String getBuildingCd() {
        return buildingCd;
    }

    public void setBuildingCd(String buildingCd) {
        this.buildingCd = buildingCd;
    }

    public String getBuildingNm() {
        return buildingNm;
    }

    public void setBuildingNm(String buildingNm) {
        this.buildingNm = buildingNm;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEntFaceImg() {
        return entFaceImg;
    }

    public void setEntFaceImg(String entFaceImg) {
        this.entFaceImg = entFaceImg;
    }

    public String getEvtDtStr() {
        return evtDtStr;
    }

    public void setEvtDtStr(String evtDtStr) {
        this.evtDtStr = evtDtStr;
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

    public String[] getSrchDeptArray() {
        return srchDeptArray;
    }

    public String getDeptArray() {
        return deptArray;
    }

    public void setDeptArray(String deptArray) {
        this.deptArray = deptArray;
    }

    public void setSrchDeptArray(String[] srchDeptArray) {
        this.srchDeptArray = srchDeptArray;
    }

    public String getIsExcel() {
        return isExcel;
    }

    public void setIsExcel(String isExcel) {
        this.isExcel = isExcel;
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
                ", evtDt=" + evtDt +
                ", entEvtTyp='" + entEvtTyp + '\'' +
                ", entEvtTypNm='" + entEvtTypNm + '\'' +
                ", terminalCd='" + terminalCd + '\'' +
                ", empCd='" + empCd + '\'' +
                ", empNm='" + empNm + '\'' +
                ", faceId=" + faceId +
                ", cardNo='" + cardNo + '\'' +
                ", cardClassTyp='" + cardClassTyp + '\'' +
                ", cardStateTyp='" + cardStateTyp + '\'' +
                ", cardTagTyp='" + cardTagTyp + '\'' +
                ", begDt=" + begDt +
                ", endDt=" + endDt +
                ", authWayTyp='" + authWayTyp + '\'' +
                ", matchScore=" + matchScore +
                ", faceThreshold=" + faceThreshold +
                ", captureAt=" + captureAt +
                ", tagAt=" + tagAt +
                ", tagCardNo='" + tagCardNo + '\'' +
                ", tagEmpCd='" + tagEmpCd + '\'' +
                ", temper=" + temper +
                ", maskConfidence=" + maskConfidence +
                ", terminalTyp='" + terminalTyp + '\'' +
                ", doorCd='" + doorCd + '\'' +
                ", doorNm='" + doorNm + '\'' +
                ", buildingCd='" + buildingCd + '\'' +
                ", buildingNm='" + buildingNm + '\'' +
                ", deptCd='" + deptCd + '\'' +
                ", deptNm='" + deptNm + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", entFaceImg='" + entFaceImg + '\'' +
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
