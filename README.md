# Hystrix-CircuitBreaker
Demo of Spring Boot application that explains the circuit breaker design pattern using Hystrix library
Call this using "http://localhost:7777/hello"
The method takes 3 seconds 
But accepted is just 1000 ms (1 second)
So, the circuit is broken and the fallback_hello() method will be called
In real project, you can put your 'STANDBY' logic

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
