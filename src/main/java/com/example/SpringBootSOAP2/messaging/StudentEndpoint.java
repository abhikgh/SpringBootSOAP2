package com.example.SpringBootSOAP2.messaging;

import java.io.StringWriter;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.SpringBootSOAP2.client.ObjectFactory;
import com.example.SpringBootSOAP2.client.Student;
import com.example.SpringBootSOAP2.client.StudentDetailsRequest;
import com.example.SpringBootSOAP2.client.StudentDetailsResponse;
import com.example.SpringBootSOAP2.entity.Employee;
import com.example.SpringBootSOAP2.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Endpoint
@Slf4j
public class StudentEndpoint {

	@Autowired
	private Marshaller requestMarshaller;

	@Autowired
	private EmployeeRepository employeeRepository;

	private static final String NAMESPACE_URI = "http://www.howtodoinjava.com/xml/school"; // targetNameSpace in WSDL

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "StudentDetailsRequest")
	@ResponsePayload
	public StudentDetailsResponse getStudent(@RequestPayload JAXBElement<StudentDetailsRequest> request) {

		ObjectFactory objectFactory = new ObjectFactory();
		StudentDetailsResponse studentDetailsResponse = objectFactory.createStudentDetailsResponse();

		try {

			StudentDetailsRequest studentDetailsRequest = request.getValue();

			Employee employee = employeeRepository.findEmpByFirstName(studentDetailsRequest.getName());
			log.info("employee :: " + employee);
			Student student = new Student();
			student.setName(employee.getFirstName().concat(employee.getLastName()));
			student.setStandard(1);
			student.setAddress(employee.getEmail());
			studentDetailsResponse.setStudent(student);

				// Marshall the request Request payload for logging
				/*StringWriter stringWriter = new StringWriter();
				requestMarshaller.marshal(request, stringWriter);
				String requestPayload = stringWriter.toString();
				log.info(requestPayload);
				*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("studentDetailsResponse :: " + studentDetailsResponse.getStudent().getName());
		return studentDetailsResponse;
	}

}
