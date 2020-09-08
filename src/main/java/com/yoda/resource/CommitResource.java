package com.yoda.resource;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yoda.entity.CommitStats;
import com.yoda.entity.CommitUser;
import com.yoda.entity.DetailedCommit;
import com.yoda.entity.RepositoryCommits;

@RestController
@RequestMapping (value = "/yoda/commits")
public class CommitResource {
	
	@Value ("${yoda.url.commits}")
	private String ENDPOINT_COMMIT;
	
	@Value ("${git.token}")
	private String GIT_ACCESS_TOKEN;
	
	private final Logger log = LoggerFactory.getLogger(CommitResource.class);
	
	@GetMapping (value = "/committers")
	private RepositoryCommits getUniqueCommitters(@RequestParam String owner, @RequestParam String repo, HttpServletResponse httpResp) {
		
		log.info(String.format("[getUniqueCommitters] Request received: owner=%s, repository=%s",owner, repo));

		Client client = null;
		try {
			
			String requestStr = ENDPOINT_COMMIT.replace("{1}", owner)
					.replace("{2}", repo);
			
			log.info("Sending request to GITHUB REST API: " + requestStr);

			client = ClientBuilder.newBuilder().build();
			WebTarget target = client.target(requestStr);
			Response resp = target.request().header("Authorization", String.format("Token %s", GIT_ACCESS_TOKEN)).get();

			log.info("Response: " + resp.getStatus() + "-" + resp.getStatusInfo());

			if (resp.getStatus() == HttpServletResponse.SC_FORBIDDEN || resp.getStatus() == HttpServletResponse.SC_BAD_REQUEST) {
				httpResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return null;
			}

			List<DetailedCommit> result = resp.readEntity(new GenericType<List<DetailedCommit>>() {});
			
			int totalCommits = result.size();
			log.info(String.format("Found %d commits", totalCommits));

			//get user participation in the last 100 commits
			
			//get latest 100 commits
			List<DetailedCommit> lastHundred = result.subList(0, result.size() > 100 ? 100 : result.size());
			
			//count commits per user (name:count)
			Map<String, Integer> userCommits = new HashMap<String, Integer>();
			Consumer<DetailedCommit> countCommitsPerUser = new Consumer<DetailedCommit>() {
				public void accept(DetailedCommit c) {
					String committerName = c.getCommit().getAuthor().getName();
					Integer count = userCommits.get(committerName);
					if(count == null || count == 0) {
						userCommits.put(committerName, 1);						
					} else {
						userCommits.replace(committerName, count+1);
					}
					
				}
			};
			lastHundred.forEach(countCommitsPerUser); //last 100 commits, count per user
			
			//get unique + recent commits per user
			Map<String, CommitUser> committers = new HashMap<String,CommitUser>();
			Consumer<DetailedCommit> getUnique = new Consumer<DetailedCommit>() {
				public void accept(DetailedCommit c) {
					CommitUser committer = c.getCommit().getAuthor();
					if (!committers.containsKey(committer.getName())) {
						Integer commitCount = userCommits.get(committer.getName());
						committer.setCommitCount(commitCount == null ? 0 : commitCount);
						
						committers.put(committer.getName(), committer);
					}
				}
			};
			result.forEach(getUnique); //all committers, past and present + their contribution based on last 100 commits

			log.info(String.format("Found %d unique committers in '%s/%s'", committers.size(), owner, repo));
			
			log.info("User-commit statistics:");
			committers.forEach((a, b) -> log.info(String.format("User: %s, Commits: %d", a, b.getCommitCount())));

			List<CommitUser> c = new ArrayList(committers.values());
			
			RepositoryCommits rc = new RepositoryCommits();
			rc.setCommits(c);
			rc.setTotalCommits(totalCommits);
			
			return rc;

		} catch (Exception ex) {
			httpResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			log.error("Exception encountered in CommitResource.getUniqueCommitters(): " + ExceptionUtils.getStackTrace(ex));
		} finally {
			httpResp.addHeader("Access-Control-Allow-Origin", "*");
			if (client != null) client.close();
		}

		return null;

	}
	
	@GetMapping (value = "/stats")
	private CommitStats getCommitStats(@RequestParam String owner, @RequestParam String repo, HttpServletResponse httpResp) {
		try {
			
			String requestStr = ENDPOINT_COMMIT.replace("{1}", owner)
					.replace("{2}", repo);
			
			log.info("Sending request to GITHUB REST API: " + requestStr);

			Client client = ClientBuilder.newBuilder().build();
			WebTarget target = client.target(requestStr);
			Response resp = target.request().header("Authorization", String.format("Token %s", GIT_ACCESS_TOKEN)).get();
			
			log.info("Response: " + resp.getStatus() + "-" + resp.getStatusInfo());

			if (resp.getStatus() == HttpServletResponse.SC_FORBIDDEN || resp.getStatus() == HttpServletResponse.SC_BAD_REQUEST) {
				httpResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return null;
			} //exit on error

			List<DetailedCommit> result = resp.readEntity(new GenericType<List<DetailedCommit>>() {});
			
			//get most recent 100 commits
			List<DetailedCommit> lastHundred = result.subList(0, result.size() > 100 ? 100 : result.size());
			
			LocalDate lOneWeek = LocalDate.now().minusWeeks(1);
			LocalDate lTwoWeeks = LocalDate.now().minusWeeks(2);
			LocalDate lThreeWeeks = LocalDate.now().minusWeeks(3);
			
			Date oneWeekAgo = Date.from(lOneWeek.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date twoWeeksAgo = Date.from(lTwoWeeks.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			Date threeWeeksAgo = Date.from(lThreeWeeks.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			
			Map<String, Integer> weeklyCommitCount = new HashMap<String, Integer>();
			Consumer<DetailedCommit> countPerWeek = new Consumer<DetailedCommit>() {
				public void accept(DetailedCommit c) {
					
					if (c.getCommit().getCommitter().getDate().after(oneWeekAgo)) {
						
						//commit was from the past week
						Integer count = weeklyCommitCount.get("1");
						if (count == null || count == 0) {
							weeklyCommitCount.put("1", 1);
						} else {
							weeklyCommitCount.replace("1", count + 1);
						}
						
					} else if (c.getCommit().getCommitter().getDate().before(oneWeekAgo) && c.getCommit().getCommitter().getDate().after(twoWeeksAgo)) {
						
						//commit was from the two weeks ago
						Integer count = weeklyCommitCount.get("2");
						if (count == null || count == 0) {
							weeklyCommitCount.put("2", 1);
						} else {
							weeklyCommitCount.replace("2", count + 1);
						}
						
					} else if (c.getCommit().getCommitter().getDate().before(twoWeeksAgo) && c.getCommit().getCommitter().getDate().after(threeWeeksAgo)) {
						
						//commit was from the three weeks ago
						Integer count = weeklyCommitCount.get("3");
						if (count == null || count == 0) {
							weeklyCommitCount.put("3", 1);
						} else {
							weeklyCommitCount.replace("3", count + 1);
						}
						
					}
					
				}
			};
			lastHundred.forEach(countPerWeek);
			weeklyCommitCount.forEach((key,count) -> log.info(String.format("%d commits on %s week(s) ago", count, key)));
			
			CommitStats cs = new CommitStats();
			try {
				cs.setCommitCountLastWeek(weeklyCommitCount.get("1"));
				cs.setCommitCountTwoWeeksAgo(weeklyCommitCount.get("2"));
				cs.setCommitCountThreeWeeksAgo(weeklyCommitCount.get("3"));
			} catch (NullPointerException ex) {
				log.warn("No data found");
			}
			return cs;
			
		} catch (Exception ex) {
			httpResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			log.error("Exception encountered in CommitResource.getCommitStats():" + ExceptionUtils.getStackTrace(ex));
		} finally {
			httpResp.addHeader("Access-Control-Allow-Origin", "*");
		}

		return null;
		
	}

}
