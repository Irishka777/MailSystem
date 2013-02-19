package com.tsystems.javaschool.mailsystem.shareableObjects;

import java.io.Serializable;

/**
 * ServerProcess class represents the answer from server to client.
 * If there is an exception (that means that client could correct that situation), 
 * then exceptionStatus set in true value and errorStatus set in false value.
 * If there is an error (that means that client could do nothing to correct the situation),
 * then both, exceptionStatus and errorStatus, set true.
 * In this both cases result value is null and exceptionMessage value is the exception (error) message.
 * If everything is ok, then exceptionStatus and errorStatus values are false, exceptionMessage value is null 
 * and result contains received from server value
 */
@SuppressWarnings("serial")
public class ServerResponse implements Serializable {
	
	private boolean exceptionStatus;
	private boolean errorStatus;
	private Object result;
	private String exceptionMessage;
	
	public ServerResponse() {}
	
	public ServerResponse(boolean exceptionStatus, boolean errorStatus, Object result, String exceptionMessage) {
		this.exceptionStatus = exceptionStatus;
		this.errorStatus = errorStatus;
		this.result = result;
		this.exceptionMessage = exceptionMessage;
	}
	
	public boolean isException() {
		return exceptionStatus;
	}
	
	public boolean isError() {
		return errorStatus;
	}
	
	public Object getResult() {
		return result;
	}
	
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	
	public String toString() {
		if (isException()) {
			return getExceptionMessage();
		}
		return (String)getResult();
	}
}
