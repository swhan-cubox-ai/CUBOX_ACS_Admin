package aero.cubox.core.vo;

public class LoginVO {


	private String login_id;
	private String login_pwd;
	private String user_nm;
	private String role_id;
	private int fdatediff;
	private String flastaccdt;
	private String flastaccip;
	private String fpasswdyn;

	private String direct_yn;

	private String dept_cd;

	private String dept_nm;


	public String getFlastaccdt() {
		return flastaccdt;
	}

	public void setFlastaccdt(String flastaccdt) {
		this.flastaccdt = flastaccdt;
	}

	public String getFlastaccip() {
		return flastaccip;
	}

	public void setFlastaccip(String flastaccip) {
		this.flastaccip = flastaccip;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getLogin_pwd() {
		return login_pwd;
	}

	public void setLogin_pwd(String login_pwd) {
		this.login_pwd = login_pwd;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public int getFdatediff() {
		return fdatediff;
	}

	public void setFdatediff(int fdatediff) {
		this.fdatediff = fdatediff;
	}

	public String getFpasswdyn() {
		return fpasswdyn;
	}

	public void setFpasswdyn(String fpasswdyn) {
		this.fpasswdyn = fpasswdyn;
	}

	public String getDirect_yn() {
		return direct_yn;
	}

	public void setDirect_yn(String direct_yn) {
		this.direct_yn = direct_yn;
	}

	public String getDept_cd() {
		return dept_cd;
	}

	public void setDept_cd(String dept_cd) {
		this.dept_cd = dept_cd;
	}

	public String getDept_nm() {
		return dept_nm;
	}

	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}

	@Override
	public String toString() {
		return "LoginVO{" +
				"login_id='" + login_id + '\'' +
				", login_pwd='" + login_pwd + '\'' +
				", user_nm='" + user_nm + '\'' +
				", role_id='" + role_id + '\'' +
				", fdatediff=" + fdatediff +
				", flastaccdt='" + flastaccdt + '\'' +
				", flastaccip='" + flastaccip + '\'' +
				", fpasswdyn='" + fpasswdyn + '\'' +
				", direct_yn='" + direct_yn + '\'' +
				", dept_cd='" + dept_cd + '\'' +
				", dept_nm='" + dept_nm + '\'' +
				'}';
	}
}
