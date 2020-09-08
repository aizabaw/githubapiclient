package com.yoda.entity;

public class CommitStats {

	private int commitCountLastWeek;
	private int commitCountTwoWeeksAgo;
	private int commitCountThreeWeeksAgo;
	
	public int getCommitCountLastWeek() {
		return commitCountLastWeek;
	}
	
	public void setCommitCountLastWeek(int commitCountLastWeek) {
		this.commitCountLastWeek = commitCountLastWeek;
	}
	
	public int getCommitCountTwoWeeksAgo() {
		return commitCountTwoWeeksAgo;
	}
	
	public void setCommitCountTwoWeeksAgo(int commitCountTwoWeeksAgo) {
		this.commitCountTwoWeeksAgo = commitCountTwoWeeksAgo;
	}
	
	public int getCommitCountThreeWeeksAgo() {
		return commitCountThreeWeeksAgo;
	}

	public void setCommitCountThreeWeeksAgo(int commitCountThreeWeeksAgo) {
		this.commitCountThreeWeeksAgo = commitCountThreeWeeksAgo;
	}
	
}
