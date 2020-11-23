package fr.pantheonsorbonne.ufr27.miage;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.NamingException;

import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;

public class AppPublisher {

	
	public static void main(String[] args) throws JMSException, NamingException, InterruptedException, IOException {

	
		
		// initialize CDI 2.0 SE container
		SeContainerInitializer initializer = SeContainerInitializer.newInstance();

		try (SeContainer container = initializer.disableDiscovery().addPackages(AppPublisher.class).initialize()) {

			// create a message produce and consumer
			final OrderPublisher orderPublisher = container.select(OrderPublisher.class).get();
				orderPublisher.publish("test1Paris", "ParisTopic");
				orderPublisher.publish("test2Paris", "ParisTopic");
				orderPublisher.publish("test1Marseille", "MarseilleTopic");

			orderPublisher.close();

		}

	}

}


//          Message     Topic
// publish(monMessage, monTopic); 
                   
//                                                Topic     Selective Consumer
// Infogare InfogareParisRetard = new Infogare ("ParisTopic", "retard") ;
