package aero.cubox.core.vo;

public class CommonVO {

    private String cd;
    private String cdNm;


    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getCdNm() {
        return cdNm;
    }

    public void setCdNm(String cdNm) {
        this.cdNm = cdNm;
    }



    @Override
    public String toString() {
        return "CommonVO{" +
                "cd='" + cd + '\'' +
                ", cdNm='" + cdNm + '\'' +
                '}';
    }
}
