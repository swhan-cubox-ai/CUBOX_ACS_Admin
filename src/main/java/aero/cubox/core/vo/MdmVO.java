package aero.cubox.core.vo;

public class MdmVO {

    private int cgpn_hr_sn;
    private int hr_no;
    private String cgpn_nm;

    private String tableTyp;
    private String card_se;
    private String issu_no;
    private String cmg_begin_dt;
    private String cmg_end_dt;
    private String creat_dt;

    private String process_yn_mdmsjsc;

    private String keyword1;

    private int srchPage			= 1;	//조회할 페이지 번호 기본 1페이지
    private int srchCnt				= 10;	//조회할 페이지 수
    private int offset				= 0;
    private int curPage				= 1;	//조회할 페이지 번호 기본 1페이지
    private int curPageUnit			= 10;	//한번에 표시할 페이지 번호 개수


    public String getTableTyp() {
        return tableTyp;
    }

    public void setTableTyp(String tableTyp) {
        this.tableTyp = tableTyp;
    }

    public int getCgpn_hr_sn() {
        return cgpn_hr_sn;
    }

    public void setCgpn_hr_sn(int cgpn_hr_sn) {
        this.cgpn_hr_sn = cgpn_hr_sn;
    }

    public int getHr_no() {
        return hr_no;
    }

    public void setHr_no(int hr_no) {
        this.hr_no = hr_no;
    }

    public String getCgpn_nm() {
        return cgpn_nm;
    }

    public void setCgpn_nm(String cgpn_nm) {
        this.cgpn_nm = cgpn_nm;
    }

    public String getCard_se() {
        return card_se;
    }

    public void setCard_se(String card_se) {
        this.card_se = card_se;
    }

    public String getIssu_no() {
        return issu_no;
    }

    public void setIssu_no(String issu_no) {
        this.issu_no = issu_no;
    }

    public String getCmg_begin_dt() {
        return cmg_begin_dt;
    }

    public void setCmg_begin_dt(String cmg_begin_dt) {
        this.cmg_begin_dt = cmg_begin_dt;
    }

    public String getCmg_end_dt() {
        return cmg_end_dt;
    }

    public void setCmg_end_dt(String cmg_end_dt) {
        this.cmg_end_dt = cmg_end_dt;
    }

    public String getCreat_dt() {
        return creat_dt;
    }

    public void setCreat_dt(String creat_dt) {
        this.creat_dt = creat_dt;
    }

    public String getProcess_yn_mdmsjsc() {
        return process_yn_mdmsjsc;
    }

    public void setProcess_yn_mdmsjsc(String process_yn_mdmsjsc) {
        this.process_yn_mdmsjsc = process_yn_mdmsjsc;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
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
        return "MdmVO{" +
                "cgpn_hr_sn=" + cgpn_hr_sn +
                ", hr_no=" + hr_no +
                ", cgpn_nm='" + cgpn_nm + '\'' +
                ", card_se='" + card_se + '\'' +
                ", issu_no='" + issu_no + '\'' +
                ", cmg_begin_dt='" + cmg_begin_dt + '\'' +
                ", cmg_end_dt='" + cmg_end_dt + '\'' +
                ", creat_dt='" + creat_dt + '\'' +
                ", process_yn_mdmsjsc='" + process_yn_mdmsjsc + '\'' +
                ", keyword1='" + keyword1 + '\'' +
                ", srchPage=" + srchPage +
                ", srchCnt=" + srchCnt +
                ", offset=" + offset +
                ", curPage=" + curPage +
                ", curPageUnit=" + curPageUnit +
                '}';
    }
}
