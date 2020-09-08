package com.yoda.entity;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties ({"fork", "forks_url", "keys_url", "collaborators_url", "teams_url", "hooks_url", "issue_events_url", "events_url", 
		"assignees_url", "branches_url", "tags_url", "blobs_url", "git_tags_url", "git_refs_url", "trees_url", "statuses_url", "languages_url", 
		"stargazers_url", "contributors_url", "subscribers_url", "subscription_url", "git_commits_url", "comments_url", "issue_comment_url", 
		"contents_url", "compare_url", "merges_url", "archive_url", "downloads_url", "issues_url", "pulls_url", "milestones_url", "notifications_url", 
		"labels_url", "releases_url", "deployments_url", "created_at", "updated_at", "pushed_at", "git_url", "ssh_url", "clone_url", "svn_url", 
		"homepage", "size", "stargazers_count", "watchers_count", "language", "has_issues", "has_projects", "has_downloads", "has_wiki", "has_pages", 
		"forks_count", "mirror_url", "archived", "disabled", "open_issues_count", "license", "forks", "open_issues", "watchers", "default_branch", "score"})
public class Repository {
	
	@JsonProperty ("id")
	private long id;
	
	@JsonProperty ("node_id")
	private String nodeId;
	
	@JsonProperty ("name")
	private String name;
	
	@JsonProperty ("full_name")
	private String fullName;
	
	@JsonProperty ("private")
	private boolean isPrivate;
	
	@JsonProperty ("html_url")
	private String htmlUrl;
	
	@JsonProperty ("description")
	private String description;
	
	@JsonProperty ("url")
	private String url;
	
	@JsonProperty ("commits_url")
	private String commitsUrl;
	
	@JsonProperty ("created_at")
	private Date createdAt;
	
	@JsonProperty ("updated_at")
	private Date updatedAt;
	
	@JsonProperty ("language")
	private String language;
	
	@JsonProperty ("owner")
	private Owner owner;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getCommitsUrl() {
		return commitsUrl;
	}

	public void setCommitsUrl(String commitsUrl) {
		this.commitsUrl = commitsUrl;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Repository [id=" + id + ", nodeId=" + nodeId + ", name=" + name + ", fullName=" + fullName
				+ ", isPrivate=" + isPrivate + ", htmlUrl=" + htmlUrl + ", description=" + description + ", url=" + url
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", language=" + language + ", owner="
				+ owner + "]";
	}
	
}
