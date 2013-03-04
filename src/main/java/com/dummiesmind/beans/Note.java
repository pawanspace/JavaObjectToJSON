package com.dummiesmind.beans;

public class Note {

	private String text;
	private Owner writer;
	
	public Note(String text, Owner writer) {
		this.text = text;
		this.writer = writer;
	}

	public String getText() {
		return text;
	}

	public Owner getWriter() {
		return writer;
	}
	
	
}
