package com.yoda.resource;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yoda.entity.Result;

@RestController
@RequestMapping (value = "/yoda/repos")
public class RepositoryResource {
	
	@Value("${yoda.url.repos}")
	private String URL_REPO;
	
	private final Logger log = LoggerFactory.getLogger(RepositoryResource.class);
	
	@GetMapping
	public Result getRepositories(@RequestParam String name, @RequestParam int size, @RequestParam int page, HttpServletResponse httpResp) {
		
		Client client = null;
		
		log.info(String.format("[getRepositories] Request received: name=%s, size=%d, page=%d",name, size, page));
		
		try {
			
			String requestStr = URL_REPO.replace("{1}", name)
					.replace("{2}", String.valueOf(size))
					.replace("{3}", String.valueOf(page));
			
			log.info("Sending request to GITHUB REST API: " + requestStr);
			
			client = ClientBuilder.newBuilder().build();
			WebTarget target = client.target(requestStr);
			Response resp = target.request().get();
			
			log.info("Response: " + resp.getStatus() + "-" + resp.getStatusInfo());
			
			if (resp.getStatus() == HttpServletResponse.SC_FORBIDDEN || resp.getStatus() == HttpServletResponse.SC_BAD_REQUEST) {
				httpResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return null;
			}

			Result res = resp.readEntity(Result.class);
			log.info(String.format("Found %d repositories that match '%s'", res.getTotalCount(), name));
			
			return res;
			
		} catch (Exception ex) {
			httpResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			log.error("Exception encountered in RepositoryResource.getRepositories() " + ExceptionUtils.getStackTrace(ex));
		} finally {
			httpResp.addHeader("Access-Control-Allow-Origin", "*");
			if (client != null) client.close();
		}
		
		return null;
		
	}
	
	

}


