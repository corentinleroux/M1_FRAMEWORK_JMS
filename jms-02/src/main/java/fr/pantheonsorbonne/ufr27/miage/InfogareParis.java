package fr.pantheonsorbonne.ufr27.miage;

import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class InfogareParis implements Closeable {

	@Inject
	@Named("Paris")
	private Topic ParisTopic;

	@Inject
	private ConnectionFactory connectionFactory;

	private Connection connection;
	private MessageConsumer messageConsumer = null ;

	private Session session;
	private String Ville ;
	
	@PostConstruct
	void init() {
		try {
			connection = connectionFactory.createConnection("nicolas", "nicolas");
			connection.setClientID("Order subscriber " + UUID.randomUUID());
			connection.start();
			session = connection.createSession();
			
			System.out.println("NON OK");
			
			//if (Ville == "Paris") {
			//	System.out.println("Test réussi");
			//	messageConsumer = session.createDurableSubscriber(ParisTopic, "Paris-subscription");
			//}
			
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}

	}
	
	public void initConsume(String Ville) {
		if (Ville == "Paris") {
			System.out.println("Test réussi");
			try {
				messageConsumer = session.createDurableSubscriber(ParisTopic, "Paris-subscription");
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String consume() {
		try {
			Message message = messageConsumer.receive();
			return ((TextMessage) message).getText();

		} catch (JMSException e) {
			System.out.println("failed to consume message ");
			return "";
		}
	}

	@Override
	public void close() throws IOException {
		try {
			messageConsumer.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			System.out.println("Failed to close JMS resources");
		}

	}
	
	public void setVille(String Ville) {
		this.Ville = Ville ;
	}


}