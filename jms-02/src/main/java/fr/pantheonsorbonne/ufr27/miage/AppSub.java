package fr.pantheonsorbonne.ufr27.miage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.NamingException;

import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;



public class AppSub {	

	
	AppSub (Topic Ville) throws IOException {
		
		SeContainerInitializer initializer = SeContainerInitializer.newInstance();
		try (SeContainer container = initializer.disableDiscovery().addPackages(AppSub.class).initialize()) {

			
			OrderSubRetard orderSubRetard = container.select(OrderSubRetard.class).get();

			while (true) {
				String message = orderSubRetard.consume();
				System.out.println("Message read from orderPublisher: " + message);
				if ("EXIT".equals(message)) {
					break;
				}
			}
			orderSubRetard.close();

		}

		System.exit(0);

	}
		
	}

