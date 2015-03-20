package com.lmiky.tiger.goods.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lmiky.jdp.database.pojo.BasePojo;

/**
 * 商品
 * @author lmiky
 * @date 2013-4-15
 */ 
@Entity
@Table(name = "t_goods")
public class Goods extends BasePojo {
	private static final long serialVersionUID = 7062654114624646911L;
	//审核状态
	public static final int AUDIT_NOAUDIT = 0; // 未经审核
	public static final int AUDIT_PASSED = 1; // 审核通过
	public static final int AUDIT_UNPASS = 2; // 审核否决

	private String title;
	private String logo;
	private String images;
	private Double salePrice;
	private Double marketPrice;
	private Double couponDiscount;	
	private String content;
	private Integer audit;
	private Date createTime;
	private Date updateTime;
	
	/**
	 * @return the title
	 */
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the logo
	 */
	@Column(name = "logo")
	public String getLogo() {
		return logo;
	}
	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * @return the images
	 */
	@Column(name = "images")
	public String getImages() {
		return images;
	}
	/**
	 * @param images the images to set
	 */
	public void setImages(String images) {
		this.images = images;
	}
	/**
	 * @return the salePrice
	 */
	@Column(name = "sale_price")
	public Double getSalePrice() {
		return salePrice;
	}
	/**
	 * @param salePrice the salePrice to set
	 */
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	/**
	 * @return the marketPrice
	 */
	@Column(name = "market_price")
	public Double getMarketPrice() {
		return marketPrice;
	}
	/**
	 * @param marketPrice the marketPrice to set
	 */
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	/**
	 * @return the couponDiscount
	 */
	@Column(name = "coupon_discount")
	public Double getCouponDiscount() {
		return couponDiscount;
	}
	/**
	 * @param couponDiscount the couponDiscount to set
	 */
	public void setCouponDiscount(Double couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	/**
	 * @return the content
	 */
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the audit
	 */
	@Column(name = "audit", updatable = false)
	public Integer getAudit() {
		return audit;
	}
	/**
	 * @param audit the audit to set
	 */
	public void setAudit(Integer audit) {
		this.audit = audit;
	}
	/**
	 * @return the createTime
	 */
	@Column(name = "create_time", updatable = false)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateTime
	 */
	@Column(name = "update_time", updatable = false)
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}