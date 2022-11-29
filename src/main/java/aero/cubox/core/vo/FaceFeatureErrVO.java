package aero.cubox.core.vo;

import java.sql.Timestamp;
import java.util.Arrays;

public class FaceFeatureErrVO {

	private Integer id;
	private String emp_cd;
	private byte[] face_img;
	private String face_state_typ;

	private String face_state_typ_nm;
	private String face_feature_typ;
	private String face_feature_typ_nm;
	private String event_typ; // 이벤트
	private String event_typ_nm; // 이벤트

	private String terminal_cd; // 단말기

	private String emp_nm; // 사원명

	private String dept_nm; // 부서명

	private String error;
	private Timestamp created_at;

	private int srchPage			= 1;	//조회할 페이지 번호 기본 1페이지
	private int srchCnt				= 10;	//조회할 페이지 수
	private int offset				= 0;
	private int curPage				= 1;	//조회할 페이지 번호 기본 1페이지
	private int curPageUnit			= 10;	//한번에 표시할 페이지 번호 개수

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

	public String getEmp_nm() {
		return emp_nm;
	}

	public void setEmp_nm(String emp_nm) {
		this.emp_nm = emp_nm;
	}

	public String getDept_nm() {
		return dept_nm;
	}

	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
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

	public String getFace_state_typ_nm() {
		return face_state_typ_nm;
	}

	public void setFace_state_typ_nm(String face_state_typ_nm) {
		this.face_state_typ_nm = face_state_typ_nm;
	}

	public String getFace_feature_typ() {
		return face_feature_typ;
	}

	public void setFace_feature_typ(String face_feature_typ) {
		this.face_feature_typ = face_feature_typ;
	}

	public String getFace_feature_typ_nm() {
		return face_feature_typ_nm;
	}

	public void setFace_feature_typ_nm(String face_feature_typ_nm) {
		this.face_feature_typ_nm = face_feature_typ_nm;
	}

	public String getEvent_typ() {
		return event_typ;
	}

	public void setEvent_typ(String event_typ) {
		this.event_typ = event_typ;
	}

	public String getEvent_typ_nm() {
		return event_typ_nm;
	}

	public void setEvent_typ_nm(String event_typ_nm) {
		this.event_typ_nm = event_typ_nm;
	}

	public String getTerminal_cd() {
		return terminal_cd;
	}

	public void setTerminal_cd(String terminal_cd) {
		this.terminal_cd = terminal_cd;
	}

	public void autoOffset(){
		int off = (this.srchPage - 1) * this.srchCnt;
		if(off<0) off = 0;
		this.offset = off;
	}

	@Override
	public String toString() {
		return "FaceFeatureErrVO{" +
				"id=" + id +
				", emp_cd='" + emp_cd + '\'' +
				//", face_img=" + Arrays.toString(face_img) +
				", face_state_typ='" + face_state_typ + '\'' +
				", face_state_typ_nm='" + face_state_typ_nm + '\'' +
				", face_feature_typ='" + face_feature_typ + '\'' +
				", face_feature_typ_nm='" + face_feature_typ_nm + '\'' +
				", event_typ='" + event_typ + '\'' +
				", event_typ_nm='" + event_typ_nm + '\'' +
				", terminal_cd='" + terminal_cd + '\'' +
				", emp_nm='" + emp_nm + '\'' +
				", dept_nm='" + dept_nm + '\'' +
				", error='" + error + '\'' +
				", created_at=" + created_at +
				", srchPage=" + srchPage +
				", srchCnt=" + srchCnt +
				", offset=" + offset +
				", curPage=" + curPage +
				", curPageUnit=" + curPageUnit +
				'}';
	}
}
