package fr.pantheonsorbonne.ufr27.miage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;



public class AppSubRetard {
	public static void main(String[] args) throws JMSException, NamingException, InterruptedException, IOException {

		// initialize CDI 2.0 SE container
		SeContainerInitializer initializer = SeContainerInitializer.newInstance();

		try (SeContainer container = initializer.disableDiscovery().addPackages(AppSubRetard.class).initialize()) {

			final OrderSubRetard orderSubRetard = container.select(OrderSubRetard.class).get();

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