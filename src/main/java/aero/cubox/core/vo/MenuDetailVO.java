package aero.cubox.core.vo;

public class MenuDetailVO extends AuthGroupSearchVO{

	private String menu_cd;
	private String menu_nm;
	private String parent_menu_cd;
	private String order_no;
	private String menu_url;

	public String getMenu_cd() {
		return menu_cd;
	}

	public void setMenu_cd(String menu_cd) {
		this.menu_cd = menu_cd;
	}

	public String getMenu_nm() {
		return menu_nm;
	}

	public void setMenu_nm(String menu_nm) {
		this.menu_nm = menu_nm;
	}

	public String getParent_menu_cd() {
		return parent_menu_cd;
	}

	public void setParent_menu_cd(String parent_menu_cd) {
		this.parent_menu_cd = parent_menu_cd;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getMenu_url() {
		return menu_url;
	}

	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}

	@Override
	public String toString() {
		return "MenuDetailVO [" + "     menu_cd=" + menu_cd +
				",     menu_nm=" + menu_nm +
				",     paretn_menu_cd=" + parent_menu_cd +
				",     order_no=" + order_no +
				",     menu_url=" + menu_url +
				"]";
	}
}
