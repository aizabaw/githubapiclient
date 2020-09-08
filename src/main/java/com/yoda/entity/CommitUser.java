package com.yoda.entity;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class CommitUser {
	
	@JsonProperty ("name")
	private String name;
	
	@JsonProperty ("email")
	private String email;
	
	@JsonProperty ("date")
	private Date date;
	
	private int commitCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getCommitCount() {
		return commitCount;
	}

	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}

	@Override
	public String toString() {
		return "CommitUser [name=" + name + ", email=" + email + ", date=" + date + ", commitCount=" + commitCount
				+ "]";
	}

}
