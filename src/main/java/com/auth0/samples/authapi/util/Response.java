package com.auth0.samples.authapi.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class Response {
	public ArrayList<String> error;
	public String message;
	public Object data;

	public Response(String message, boolean error) {
		if(error) {
			this.error = new ArrayList<String>();
			this.error.add(message);
		} else {
			this.message=message;
		}
	}

	public Response(String message, boolean error, Object data) {
		this(message,error);
		this.data = data;
	}

	public Response(BindingResult error) {
		ArrayList<String> msg = new ArrayList<>();
		List<FieldError> errors = error.getFieldErrors();
		errors.forEach(item -> {
			msg.add(item.getField()+ " "+item.getDefaultMessage());
		});
		this.error = msg;
	}
}
