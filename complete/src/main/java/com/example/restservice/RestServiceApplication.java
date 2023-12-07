package com.example.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@RestController
public class RestServiceApplication {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@GetMapping("/objectname")
	public ObjectName GetMBean() {
		ObjectName objectName = null;
		try {
			objectName = new ObjectName("com.example.restservice:type=basic,name=game");
			MBeanServer server = ManagementFactory.getPlatformMBeanServer();
			server.registerMBean(new Game(), objectName);
		} catch (MalformedObjectNameException | InstanceAlreadyExistsException |
				 MBeanRegistrationException | NotCompliantMBeanException e) {
		}
		return objectName;
	}
}

record Greeting(long id, String content) { }
