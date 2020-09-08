package com.yoda.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties ({"tree", "url", "comment_count", "verification"})
public class Commit {
	
	@JsonProperty
	private CommitUser author;
	
	@JsonProperty
	private CommitUser committer;

	@JsonProperty
	private String message;

	public CommitUser getAuthor() {
		return author;
	}

	public void setAuthor(CommitUser author) {
		this.author = author;
	}

	public CommitUser getCommitter() {
		return committer;
	}

	public void setCommitter(CommitUser committer) {
		this.committer = committer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Commit [author=" + author + ", committer=" + committer + ", message=" + message + "]";
	}
	
}
