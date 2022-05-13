package com.example.demo.model;

import java.util.List;

public class ResponseWrapper<T> {

	private String message;
	private Boolean status;
	private Long total;
	private String errorCode;
	private List<T> content;

	public ResponseWrapper(String message, Boolean status, String errorCode, List<T> content) {
		super();
		this.message = message;
		this.status = status;
		this.errorCode = errorCode;
		this.content = content;
	}

	public ResponseWrapper(String message, Boolean status, List<T> content) {
		this.message = message;
		this.status = status;
		this.content = content;
	}
	
	public ResponseWrapper(String message, Boolean status, Long total, List<T> content) {
		this.message = message;
		this.status = status;
		this.total = total;
		this.content = content;
	}

	public ResponseWrapper(String message, Boolean status, Long total, String errorCode, List<T> content) {
		super();
		this.message = message;
		this.status = status;
		this.total = total;
		this.errorCode = errorCode;
		this.content = content;
	}

	public ResponseWrapper() {
		// TODO Auto-generated constructor stub
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ResponseWrapper{" +
				"message='" + message + '\'' +
				", status=" + status +
				", total=" + total +
				", errorCode='" + errorCode + '\'' +
				", content=" + content +
				'}';
	}
}
