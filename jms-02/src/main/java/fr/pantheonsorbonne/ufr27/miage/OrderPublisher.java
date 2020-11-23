package fr.pantheonsorbonne.ufr27.miage;

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

public class OrderPublisher implements Closeable {

	@Inject
	@Named("Paris")
	Topic ParisTopic;
	
	@Inject
	@Named("Marseille")
	Topic MarseilleTopic;
	
	@Inject
	ConnectionFactory connectionFactory;

	Connection connection;
	Session session;
	MessageProducer messagePublisher;
	MessageProducer messagePublisher2;

	int index = 0;

	@PostConstruct
	private void init() {
		try {
			this.connection = connectionFactory.createConnection("nicolas","nicolas");
			connection.start();
			this.session = connection.createSession();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}

	}

	public String publish(String message) {
		try {
			
			// Le publisher envoi sur MarseilleTopic et ParisTopic
			this.messagePublisher = session.createProducer(MarseilleTopic);
			this.messagePublisher2 = session.createProducer(ParisTopic);
			this.messagePublisher.send(this.session.createTextMessage(message));
			this.messagePublisher2.send(this.session.createTextMessage(message));
			return message;
		} catch (JMSException e) {
			System.out.println("Failed to send message to queue");
			return "Nothing sent";
		}
	}

	@Override
	public void close() throws IOException {
		try {
			messagePublisher.close();
			messagePublisher2.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			System.out.println("failed to close JMS resources");
		}

	}

}
