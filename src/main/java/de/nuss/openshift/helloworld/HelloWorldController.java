package de.nuss.openshift.helloworld;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HelloWorldController {

	@Autowired
	private BuildProperties buildProperties;

	/**
	 * This methods logs some statements.
	 * 
	 * @return
	 * @throws UnknownHostException
	 */
	@GetMapping(produces = "text/plain")
	public String helloWorld(HttpServletRequest request) throws UnknownHostException {
		log.info("service called");

		StringBuilder sb = new StringBuilder();

		sb.append(String.format("Hello Open Shift, it is %s", ZonedDateTime.now())).append("\r\n");
		sb.append("================================================================================================")
				.append("\r\n");
		sb.append("dummy text 2").append("\r\n");

		sb.append("\r\n");
		sb.append("System Information").append("\r\n");
		sb.append("-----------------------------------------------------------------------------------------------")
				.append("\r\n");
		;
		sb.append("hostname=").append(InetAddress.getLocalHost().getHostName().toString()).append("\r\n");
		sb.append("cpu core=").append(Runtime.getRuntime().availableProcessors()).append("\r\n");
		sb.append("max memory=").append(Runtime.getRuntime().maxMemory() / 1024. / 1024.).append("MB\r\n");
		sb.append("free memory=").append(Runtime.getRuntime().freeMemory() / 1024. / 1024.).append("MB\r\n");
		sb.append("total memory=").append(Runtime.getRuntime().totalMemory() / 1024. / 1024.).append("MB\r\n");

		sb.append("java version: ").append(System.getProperty("java.version")).append("\r\n");
		sb.append("java runtime version: ").append(System.getProperty("java.runtime.version")).append("\r\n");
		sb.append("java runtime name: ").append(System.getProperty("java.runtime.name")).append("\r\n");

		sb.append("\r\n");
		sb.append("Build Information").append("\r\n");
		sb.append("-----------------------------------------------------------------------------------------------")
				.append("\r\n");
		;
		sb.append("name: ").append(buildProperties.getName()).append("\r\n");
		sb.append("version: ").append(buildProperties.getVersion()).append("\r\n");
		sb.append("build time: ").append(ZonedDateTime.ofInstant(buildProperties.getTime(), ZoneId.systemDefault()))
				.append("\r\n");
		sb.append("artifact: ").append(buildProperties.getArtifact()).append("\r\n");
		sb.append("group: ").append(buildProperties.getGroup()).append("\r\n");

		if (request.getParameter("debug") != null) {
			sb.append("\r\n");
			sb.append("Java Properties").append("\r\n");
			sb.append("-----------------------------------------------------------------------------------------------")
					.append("\r\n");
			System.getProperties().forEach((key, value) -> {
				sb.append(key).append(": ").append(value).append("\r\n");
			});
		}
		return sb.toString();
	}
}
