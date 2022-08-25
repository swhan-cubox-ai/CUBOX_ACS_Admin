package aero.cubox.core.vo;

public class RoleVO extends AuthGroupSearchVO{

	private String role_id;
	private String role_nm;
	private String system_yn;
	private String delete_yn;

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getRole_nm() {
		return role_nm;
	}

	public void setRole_nm(String role_nm) {
		this.role_nm = role_nm;
	}

	public String getSystem_yn() {
		return system_yn;
	}

	public void setSystem_yn(String system_yn) {
		this.system_yn = system_yn;
	}

	public String getDelete_yn() {
		return delete_yn;
	}

	public void setDelete_yn(String delete_yn) {
		this.delete_yn = delete_yn;
	}

	@Override
	public String toString() {
		return "RoleVO [" + "     role_id=" + role_id +
				",     role_nm=" + role_nm +
				",     system_yn=" + system_yn +
				",     delete_yn=" + delete_yn +
				"]";
	}
}
