package net.javaguides.springboot.model;

import org.springframework.stereotype.Component;

@Component
public class ResponseBean 
{
	private Integer statusCD;
	private String statusMSG;
	private Long responseTime;
	private Object responseData;
	private String errorCode;

	public Integer getStatusCD() {
		return statusCD;
	}

	public void setStatusCD(Integer statusCD) {
		this.statusCD = statusCD;
	}

	public String getStatusMSG() {
		return statusMSG;
	}

	public void setStatusMSG(String statusMSG) {
		this.statusMSG = statusMSG;
	}

	public Long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}

	public Object getResponseData() {
		return responseData;
	}

	public void setObject(Object responseData) {
		this.responseData = responseData;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
