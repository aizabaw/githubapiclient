package com.yoda.entity;

import java.util.List;

public class RepositoryCommits {
	
	private int totalCommits;
	private List<CommitUser> commits;
	
	public int getTotalCommits() {
		return totalCommits;
	}
	
	public void setTotalCommits(int totalCommits) {
		this.totalCommits = totalCommits;
	}
	
	public List<CommitUser> getCommits() {
		return commits;
	}
	
	public void setCommits(List<CommitUser> commits) {
		this.commits = commits;
	}
	

}
