package com.dummiesmind.beans;

import java.util.List;

public class Task {
	
	private String description;
	private Owner assignee;
	private List<Note> notes;
	
	public Task(Owner assignee, List<Note> notes, String description) {
		this.assignee = assignee;
		this.notes = notes;
		this.description = description;
	}
	
	public Owner getAssignee() {
		return assignee;
	}
	
	public List<Note> getNotes() {
		return notes;
	}

	public String getDescription() {
		return description;
	}

	
}
