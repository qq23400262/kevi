package org.caco.taobao.domain;

public class Inventory {
	private String year;
	private String no;
	private String jijie;
	private String typeName;
	private Integer price;
	private String color;
	private Integer ss;
	private Integer sm;
	private Integer sl;
	private Integer sxl;
	private Integer sxxl;
	private Integer qty;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getJijie() {
		return jijie;
	}
	public void setJijie(String jijie) {
		this.jijie = jijie;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getSs() {
		return ss;
	}
	public void setSs(Integer ss) {
		this.ss = ss;
	}
	public Integer getSm() {
		return sm;
	}
	public void setSm(Integer sm) {
		this.sm = sm;
	}
	public Integer getSl() {
		return sl;
	}
	public void setSl(Integer sl) {
		this.sl = sl;
	}
	public Integer getSxl() {
		return sxl;
	}
	public void setSxl(Integer sxl) {
		this.sxl = sxl;
	}
	public Integer getSxxl() {
		return sxxl;
	}
	public void setSxxl(Integer sxxl) {
		this.sxxl = sxxl;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	@Override
	public String toString() {
		return "款式编号：" + no + ", 颜色:" + color + ", S=" + ss + ", M=" + sm + ", L=" + sl + ", XL=" + sxl + ", XXL="
				+ sxxl;
	}

}
