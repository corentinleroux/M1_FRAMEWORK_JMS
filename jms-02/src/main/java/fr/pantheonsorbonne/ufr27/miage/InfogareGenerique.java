package fr.pantheonsorbonne.ufr27.miage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;



public class InfogareGenerique {
	String Ville = null;
	public void initInfogare() throws JMSException, NamingException, InterruptedException, IOException {

		// initialize CDI 2.0 SE container
		SeContainerInitializer initializer = SeContainerInitializer.newInstance();

		try (SeContainer container = initializer.disableDiscovery().addPackages(InfogareGenerique.class).initialize()) {

			System.out.println(Ville);
			InfogareSub Infogare = container.select(InfogareSub.class).get();
			Infogare.initConsume(Ville);
			
	
			while (true) {
				String message = Infogare.consume();
				System.out.println("Message read from orderPublisher: " + message);
				if ("EXIT".equals(message)) {
					break;
				}
			}
			Infogare.close();

		}

		System.exit(0);

	}
	public InfogareGenerique(String s) throws JMSException, NamingException, InterruptedException, IOException {
		this.Ville = s ; 
		
	}
}