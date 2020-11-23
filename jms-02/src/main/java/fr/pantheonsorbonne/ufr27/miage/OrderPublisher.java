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
	MessageProducer PublishParis;
	MessageProducer PublishMarseille;

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

	public String publish(String message, String Topic) {
		try {
			
				if (Topic == "ParisTopic") {
					this.PublishParis = session.createProducer(ParisTopic);
					this.PublishParis.send(this.session.createTextMessage(message));
				}
				else if (Topic == "LyonTopic") {
					this.PublishParis = session.createProducer(ParisTopic);
					this.PublishParis.send(this.session.createTextMessage(message));
				}
			return message;
			
			
		} catch (JMSException e) {
			System.out.println("Failed to send message to queue");
			return "Nothing sent";
		}
	}

	@Override
	public void close() throws IOException {
		try {
			PublishParis.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			System.out.println("failed to close JMS resources");
		}

	}

}
