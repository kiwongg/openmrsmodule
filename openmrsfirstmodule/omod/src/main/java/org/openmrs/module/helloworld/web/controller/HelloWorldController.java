package org.openmrs.module.helloworld.web.controller;

import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/helloworld")
public class HelloWorldController extends BaseRestController {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String sayHello() {
		return "Hello World from OpenMRS!";
	}
}