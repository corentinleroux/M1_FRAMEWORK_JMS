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

		InfogareGenerique Infogare = new InfogareGenerique("Lyon");
		InfogareGenerique Infogare2 = new InfogareGenerique("Marseille");
		
		Infogare.initInfogare();
		Infogare2.initInfogare();
	}
}
