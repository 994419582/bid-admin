package cn.teleinfo.bidadmin.app.entity;


public class IdentityEntity {
	private String xM;//姓名
	private String gMSFZHM;//公民身份证号码
	private String yXQQSRQ;//有效期起始日期
	private String yXQJZRQ;//有效期截止日期
	private String photoEncode;//人像图片的编码过的字节数组字符串
	@Override
	public String toString() {
		return "IdentityEntity [xM=" + xM + ", gMSFZHM=" + gMSFZHM + ", yXQQSRQ=" + yXQQSRQ
				+ ", yXQJZRQ=" + yXQJZRQ + "]";
	}
	public String getxM() {
		return xM;
	}
	public void setxM(String xM) {
		this.xM = xM;
	}
	public String getgMSFZHM() {
		return gMSFZHM;
	}
	public void setgMSFZHM(String gMSFZHM) {
		this.gMSFZHM = gMSFZHM;
	}
	public String getyXQQSRQ() {
		return yXQQSRQ;
	}
	public void setyXQQSRQ(String yXQQSRQ) {
		this.yXQQSRQ = yXQQSRQ;
	}
	public String getyXQJZRQ() {
		return yXQJZRQ;
	}
	public void setyXQJZRQ(String yXQJZRQ) {
		this.yXQJZRQ = yXQJZRQ;
	}
}
