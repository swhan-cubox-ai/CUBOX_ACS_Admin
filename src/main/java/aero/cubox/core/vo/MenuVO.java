package aero.cubox.core.vo;

import java.util.List;

public class MenuVO extends AuthGroupSearchVO{

	private String menu_cd;
	private String menu_nm;
	private String parent_menu_cd;
	private String order_no;
	private String icon_img;

	private List<MenuDetailVO> list;

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

	public void setParent_menu_cd(String paretn_menu_cd) {
		this.parent_menu_cd = paretn_menu_cd;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public List<MenuDetailVO> getList() {
		return list;
	}

	public void setList(List<MenuDetailVO> list) {
		this.list = list;
	}

	public String getIcon_img() {
		return icon_img;
	}

	public void setIcon_img(String icon_img) {
		this.icon_img = icon_img;
	}

	@Override
	public String toString() {
		return "MenuVO [" + "     menu_cd=" + menu_cd +
				",     menu_nm=" + menu_nm +
				",     parent_menu_cd=" + parent_menu_cd +
				",     order_no=" + order_no +
				",     icon_img=" + icon_img +
				",     list=" + list +
				"]";
	}
}
