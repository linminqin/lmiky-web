package com.lmiky.jdp.system.menu.model;

/**
 * 子菜单菜单
 * @author lmiky
 * @date 2013-6-16
 */
public class SubMenu  extends Menu {
	private static final long serialVersionUID = 3583468450044207384L;
	public static final String TYPE_LINK = "link";
	public static final String TYPE_DIALOG = "dialog";
	public static final String TYPE_IFRAME = "iframe";
	
	private String url;
	private String type;
	private String modulePath;
	private String authority;
	private Integer width;
	private Integer height;
	private String iframeurl;
	private LeftMenu leftMenu;
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the modulePath
	 */
	public String getModulePath() {
		return modulePath;
	}
	/**
	 * @param modulePath the modulePath to set
	 */
	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}
	/**
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}
	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}
	/**
	 * @return the iframeurl
	 */
	public String getIframeurl() {
		return iframeurl;
	}
	/**
	 * @param iframeurl the iframeurl to set
	 */
	public void setIframeurl(String iframeurl) {
		this.iframeurl = iframeurl;
	}
	/**
	 * @return the leftMenu
	 */
	public LeftMenu getLeftMenu() {
		return leftMenu;
	}
	/**
	 * @param leftMenu the leftMenu to set
	 */
	public void setLeftMenu(LeftMenu leftMenu) {
		this.leftMenu = leftMenu;
	}
}