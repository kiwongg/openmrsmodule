package org.openmrs.module.helloworld;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.BaseModuleActivator;

public class HelloWorldActivator extends BaseModuleActivator {

	private static final Log log = LogFactory.getLog(HelloWorldActivator.class);

	@Override
	public void started() {
		log.info("Hello World Module started");
	}

	@Override
	public void stopped() {
		log.info("Hello World Module stopped");
	}
}