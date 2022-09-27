package aero.cubox.core.vo;

public class AuthMenuTreeVO {

    private String menuId;
    private String menuNm;
    private int depth;
    private String parentMenuId;




    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }



    @Override
    public String toString() {
        return "AuthMenuTreeVO{" +
                "menuId='" + menuId + '\'' +
                ", menuNm='" + menuNm + '\'' +
                ", depth=" + depth +
                ", parentMenuId='" + parentMenuId + '\'' +
                '}';
    }
}
