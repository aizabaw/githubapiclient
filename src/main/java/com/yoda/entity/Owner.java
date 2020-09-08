package com.yoda.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties ({"avatar_url", "gravatar_id", "followers_url", "following_url", 
	"gists_url", "starred_url", "subscriptions_url", "organizations_url",
	"repos_url", "events_url", "received_events_url", "type", "site_admin"})
public class Owner {
	
	@JsonProperty ("login")
	private String login;
	
	@JsonProperty ("id")
	private String id;
	
	@JsonProperty ("node_id")
	private String nodeId;
	
	@JsonProperty ("url")
	private String url;
	
	@JsonProperty ("html_url")
	private String htmlUrl;
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNodeId() {
		return nodeId;
	}
	
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getHtmlUrl() {
		return htmlUrl;
	}
	
	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	@Override
	public String toString() {
		return "Owner [login=" + login + ", id=" + id + ", nodeId=" + nodeId + ", url=" + url + ", htmlUrl=" + htmlUrl
				+ "]";
	}
	
}