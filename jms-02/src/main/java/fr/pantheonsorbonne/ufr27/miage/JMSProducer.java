package fr.pantheonsorbonne.ufr27.miage;

import java.util.Hashtable;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory;

/**
 * THis class produces bean to be injected in JMS Classes
 * 
 * @author nherbaut
 *
 */
public class JMSProducer {

	// fake JNDI context to create object
	private static final Context JNDI_CONTEXT;

	static {
		Hashtable<String, String> jndiBindings = new Hashtable<>();
		jndiBindings.put(Context.INITIAL_CONTEXT_FACTORY, ActiveMQInitialContextFactory.class.getName());
		jndiBindings.put("connectionFactory.ConnectionFactory", "tcp://localhost:61616");
		jndiBindings.put("topic.retard", "retard");
		Context c = null;
		try {
			c = new InitialContext(jndiBindings);
		} catch (NamingException e) {
			e.printStackTrace();
			c = null;
			System.exit(-1);

		} finally {
			JNDI_CONTEXT = c;
		}
	}

	@Produces
	@Named("retard")
	public Topic getJMSQueue() throws NamingException {
		return (Topic) JNDI_CONTEXT.lookup("retard");
	}

	@Produces

	public ConnectionFactory getJMSConnectionFactory() throws NamingException {
		return (ConnectionFactory) JNDI_CONTEXT.lookup("ConnectionFactory");
	}

}
