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

public class OrderSubRetard implements Closeable {

	@Inject
	@Named("ville")
	private Topic Paris;
	
	@Inject
	@Named("ville")
	private Topic Lyon;
	

	@Inject
	private ConnectionFactory connectionFactory;

	private Connection connection;
	private MessageConsumer messageConsumer;

	private Session session;

	@PostConstruct
	void init() {
		try {
			connection = connectionFactory.createConnection("nicolas", "nicolas");
			connection.setClientID("Order subscriber " + UUID.randomUUID());
			connection.start();
			session = connection.createSession();
			messageConsumer = session.createDurableSubscriber(Paris, "order-subscription");
		} catch (JMSException e) {
			throw new RuntimeException(e);
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

}
