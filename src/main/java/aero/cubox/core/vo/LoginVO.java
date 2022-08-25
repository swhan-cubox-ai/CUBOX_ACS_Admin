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

	@Override
	public String toString() {
		return "Login2VO [" + "     login_id=" + login_id +
				",     login_pwd=" + login_pwd +
				",     user_nm=" + user_nm +
				",     fdatediff=" + fdatediff +
				",     flastaccdt=" + flastaccdt +
				",     flastaccip=" + flastaccip +
				"]";
	}
}
