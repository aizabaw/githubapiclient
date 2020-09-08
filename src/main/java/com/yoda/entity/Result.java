package com.yoda.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Result {

	@JsonProperty ("total_count")
	private int totalCount;
	
	@JsonProperty ("incomplete_results")
	private boolean incompleteResults;
	
	@JsonProperty ("items")
	private List<Repository> repositories;
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public boolean isIncompleteResults() {
		return incompleteResults;
	}
	
	public void setIncompleteResults(boolean incompleteResults) {
		this.incompleteResults = incompleteResults;
	}
	
	public List<Repository> getRepositories() {
		return repositories;
	}
	
	public void setRepositories(List<Repository> repositories) {
		this.repositories = repositories;
	}

	@Override
	public String toString() {
		return "Result [totalCount=" + totalCount + ", incompleteResults=" + incompleteResults + ", repositories="
				+ repositories + "]";
	}
	
}
