package com.yoda.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties ({"url", "html_url", "comments_url", "author", "committer", "parents"})
public class DetailedCommit {
	
	@JsonProperty
	private String sha;
	
	@JsonProperty ("node_id")
	private String nodeId;
	
	@JsonProperty
	private Commit commit;

	public String getSha() {
		return sha;
	}
	
	public void setSha(String sha) {
		this.sha = sha;
	}
	
	public String getNodeId() {
		return nodeId;
	}
	
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	public Commit getCommit() {
		return commit;
	}
	
	public void setCommit(Commit commit) {
		this.commit = commit;
	}

	@Override
	public String toString() {
		return "DetailedCommit [sha=" + sha + ", nodeId=" + nodeId + ", commit=" + commit + "]";
	}
	
}
