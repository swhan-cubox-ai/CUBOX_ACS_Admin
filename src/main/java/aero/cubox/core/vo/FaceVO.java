package aero.cubox.core.vo;


import java.sql.Timestamp;

public class FaceVO {

    private Integer id;
    private String emp_cd;
    private byte[] face_img;
    private String face_state_typ;

    private Integer emp_id;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmp_cd() {
        return emp_cd;
    }

    public void setEmp_cd(String emp_cd) {
        this.emp_cd = emp_cd;
    }

    public byte[] getFace_img() {
        return face_img;
    }

    public void setFace_img(byte[] face_img) {
        this.face_img = face_img;
    }

    public String getFace_state_typ() {
        return face_state_typ;
    }

    public void setFace_state_typ(String face_state_typ) {
        this.face_state_typ = face_state_typ;
    }

    public Integer getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
