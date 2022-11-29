package aero.cubox.core.vo;

import java.sql.Timestamp;

public class EntHistBioVO {

    private int ent_hist_id;
    private String ent_face_img;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public int getEnt_hist_id() {
        return ent_hist_id;
    }

    public void setEnt_hist_id(int ent_hist_id) {
        this.ent_hist_id = ent_hist_id;
    }

    public String getEnt_face_img() {
        return ent_face_img;
    }

    public void setEnt_face_img(String ent_face_img) {
        this.ent_face_img = ent_face_img;
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

    @Override
    public String toString() {
        return "EntHistBioVO{" +
                "ent_hist_id=" + ent_hist_id +
                ", ent_face_img='" + ent_face_img + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
