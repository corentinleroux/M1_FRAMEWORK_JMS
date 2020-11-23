package fr.pantheonsorbonne.ufr27.miage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;



public class AppInfogare {
	public static void main(String[] args) throws JMSException, NamingException, InterruptedException, IOException {

	/*	// initialize CDI 2.0 SE container
		SeContainerInitializer initializer = SeContainerInitializer.newInstance();

		try (SeContainer container = initializer.disableDiscovery().addPackages(AppInfogare.class).initialize()) {

			System.out.println("Paris");
			InfogareSub Infogare = container.select(InfogareSub.class).get();
			Infogare.initConsume("Paris");
			
	
			while (true) {
				String message = Infogare.consume();
				System.out.println("Message read from orderPublisher: " + message);
				if ("EXIT".equals(message)) {
					break;
				}
			}
			Infogare.close();

		}

		System.exit(0); */

		AppInfogareLyon Infogare = new AppInfogareLyon();
		AppInfogareLyon Infogare2 = new AppInfogareLyon();
		
		Infogare.initInfogare("Marseille");
	}
}
