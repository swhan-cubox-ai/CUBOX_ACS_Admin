package aero.cubox.core.vo;

public class HolidayVO {

    private int id;
    private String holidayTyp;
    private String holidayTypNm;
    private String holidayCd;
    private String holidayNm;
    private String holiday;
    private String createdAt;
    private String updatedAt;
    private String useYn;
    private String startDt;
    private String endDt;



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

    public String getHolidayNm() {
        return holidayNm;
    }

    public void setHolidayNm(String holidayNm) {
        this.holidayNm = holidayNm;
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

    public String getHolidayTypNm() {
        return holidayTypNm;
    }

    public void setHolidayTypNm(String holidayTypNm) {
        this.holidayTypNm = holidayTypNm;
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

    public String getHolidayTyp() {
        return holidayTyp;
    }

    public void setHolidayTyp(String holidayTyp) {
        this.holidayTyp = holidayTyp;
    }

    public String getHolidayCd() {
        return holidayCd;
    }

    public void setHolidayCd(String holidayCd) {
        this.holidayCd = holidayCd;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }


    public void autoOffset(){
        int off = (this.srchPage - 1) * this.srchCnt;
        if(off<0) off = 0;
        this.offset = off;
    }

    @Override
    public String toString() {
        return "HolidayVO{" +
                "id=" + id +
                ", holidayTyp='" + holidayTyp + '\'' +
                ", holidayTypNm='" + holidayTypNm + '\'' +
                ", holidayCd='" + holidayCd + '\'' +
                ", holidayNm='" + holidayNm + '\'' +
                ", holiday='" + holiday + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", useYn='" + useYn + '\'' +
                ", startDt='" + startDt + '\'' +
                ", endDt='" + endDt + '\'' +
                ", srchCond='" + srchCond + '\'' +
                ", keyword='" + keyword + '\'' +
                ", srchPage=" + srchPage +
                ", srchCnt=" + srchCnt +
                ", offset=" + offset +
                ", curPage=" + curPage +
                ", curPageUnit=" + curPageUnit +
                '}';
    }
}
