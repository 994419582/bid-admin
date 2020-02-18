package cn.teleinfo.bidadmin.app.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
public class Result {
	private String xM;
	private String gMSFZHM;
	private String timeStamp;
	private String businessSerialNumber;
	private String authResult;
	private String success;
	private String errorDesc;
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
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getBusinessSerialNumber() {
		return businessSerialNumber;
	}
	public void setBusinessSerialNumber(String businessSerialNumber) {
		this.businessSerialNumber = businessSerialNumber;
	}
	public String getAuthResult() {
		return authResult;
	}
	public void setAuthResult(String authResult) {
		this.authResult = authResult;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

}
