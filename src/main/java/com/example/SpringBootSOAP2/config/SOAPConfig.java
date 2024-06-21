package com.example.SpringBootSOAP2.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

import java.util.HashMap;
import java.util.Map;

@EnableWs
@Configuration
public class SOAPConfig extends WsConfigurerAdapter {
	
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(applicationContext); // applicationContext to find other beans 
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(messageDispatcherServlet, "/service/*");  // http://localhost:8080/service/
	}

	@Bean(name="studentDetails") // bean name = wsdl name
	public Wsdl11Definition wsdl11Definition() {
		SimpleWsdl11Definition simpleWsdl11Definition = new SimpleWsdl11Definition();
		simpleWsdl11Definition.setWsdl(new ClassPathResource("/wsdl/studentDetails.wsdl")); // http://localhost:8080/service/wsdl/studentDetails.wsdl
		return simpleWsdl11Definition;
	}
	
	/*@Bean("requestMarshaller")
	public Marshaller requestMarshaller() throws JAXBException {
		Marshaller marshaller = JAXBContext.newInstance("com.example.SpringBootSOAP2.client").createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return marshaller;
	}
*/
	@Bean("requestMarshaller")
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPath("com.example.SpringBootSOAPClient2.client"); //generatePackage in pom.xml
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		jaxb2Marshaller.setMarshallerProperties(properties);
		jaxb2Marshaller.setMtomEnabled(Boolean.TRUE);
		return jaxb2Marshaller;
	}

}