package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegrationManagement;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.config.EnableIntegrationGraphController;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.integration.support.management.graph.IntegrationGraphServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@SpringBootApplication
@IntegrationComponentScan
@EnableIntegrationManagement
@EnableIntegrationGraphController(allowedOrigins = "http://localhost:8082")
@EnableWebMvc
public class DemoApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
//		TempConverter converter = ctx.getBean(TempConverter.class);
//		System.out.println(converter.fahrenheitToCelcius(68.0f));
//		ctx.close();
	}

	@MessagingGateway
	public interface TempConverter {

		@Gateway(requestChannel = "convert.input")
		String fahrenheitToCelcius(float fahren);

	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder)
	{
		return restTemplateBuilder
				.setConnectTimeout(10000).setReadTimeout(10000).build();
	}

	@Autowired
	private RestTemplate restTemplate;

	private HttpRequestExecutingMessageHandler restHandler() {
		HttpRequestExecutingMessageHandler restHandler = new HttpRequestExecutingMessageHandler("http://localhost:8888", restTemplate);
		restHandler.setExpectedResponseType(String.class);
		restHandler.setHttpMethod(HttpMethod.GET);
		return restHandler;
	}

	@Bean
	public IntegrationFlow convert() {
		return f -> f
//				.transform(payload ->
//						"<FahrenheitToCelsius xmlns=\"https://www.w3schools.com/xml/\">"
//								+     "<Fahrenheit>" + payload + "</Fahrenheit>"
//								+ "</FahrenheitToCelsius>")
//				.enrichHeaders(h -> h
//						.header(WebServiceHeaders.SOAP_ACTION,
//								"https://www.w3schools.com/xml/FahrenheitToCelsius"))
				.handle(restHandler());
//				.handle(Http.outboundGateway("http://localhost:8888", restTemplate).expectedResponseType(String.class));
//				.transform(new ObjectToStringTransformer());
//				.transform(new XPathTransformer("/*[local-name()=\"FahrenheitToCelsiusResponse\"]"
//						+ "/*[local-name()=\"FahrenheitToCelsiusResult\"]"));
	}

	@Bean
	public IntegrationGraphServer graphServer() {
		return new IntegrationGraphServer();
	}
}
