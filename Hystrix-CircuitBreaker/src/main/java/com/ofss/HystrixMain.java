package com.ofss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@SpringBootApplication
@RestController
@EnableHystrix
public class HystrixMain {

	public static void main(String[] args) {
		SpringApplication.run(HystrixMain.class, args);
		System.out.println("Hystrix started");

	}
	
	@GetMapping("/hello")
	@HystrixCommand(fallbackMethod="fallback_hello", commandProperties= {
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1000")
	})
	public String m1() throws InterruptedException
	{
		Thread.sleep(3000); // sleep for 3 seconds
		// From here, using RestTemplate, you can call another MS
		// In that MS, introduce some delay
		
		return "Hello World";
	}
	
	private String fallback_hello()
	{
		System.out.println("circuit has been broken, so the requests will not be sent the hello method");
		return "Request Fails...It takes long time to responde...try sometimes later";
	}

}
