package aero.cubox.core.vo;

import java.sql.Timestamp;

public class EntHistStatVO {
    private Timestamp totDate;
    private int cnt;
    private String cardTagTyp;
    private String srchCond1; //인증수단
    private String srchCond2; //building
    private String srchCond3; // 층
    private String srchCond4; // 일/월
    private String keyword;

    private String fromDt;
    private String toDt;

    private int srchPage			= 1;	//조회할 페이지 번호 기본 1페이지
    private int srchCnt				= 10;	//조회할 페이지 수
    private int offset				= 0;
    private int curPage				= 1;	//조회할 페이지 번호 기본 1페이지
    private int curPageUnit			= 10;	//한번에 표시할 페이지 번호 개수

    public Timestamp getTotDate() {
        return totDate;
    }

    public void setTotDate(Timestamp totDate) {
        this.totDate = totDate;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getCardTagTyp() {
        return cardTagTyp;
    }

    public void setCardTagTyp(String cardTagTyp) {
        this.cardTagTyp = cardTagTyp;
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

    public String getSrchCond3() {
        return srchCond3;
    }

    public void setSrchCond3(String srchCond3) {
        this.srchCond3 = srchCond3;
    }

    public String getSrchCond4() {
        return srchCond4;
    }

    public void setSrchCond4(String srchCond4) {
        this.srchCond4 = srchCond4;
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
}
