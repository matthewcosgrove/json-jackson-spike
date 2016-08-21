package com.mc.model;

import com.fasterxml.jackson.annotation.JsonCreator;

// Love Immutability? See http://www.cowtowncoder.com/blog/archives/2010/08/entry_409.html
// Also see findings from cf java client
public class TrivialResponse {

	private final String body;

	@JsonCreator
	public TrivialResponse(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

}
