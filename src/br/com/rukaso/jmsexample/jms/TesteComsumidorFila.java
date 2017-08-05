package br.com.rukaso.jmsexample.jms;

import java.util.Enumeration;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteComsumidorFila {

	public static void main(String[] args) throws NamingException, JMSException {
	    System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");

		InitialContext context = new InitialContext();
		
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection("user", "senha");
		connection.start();
		
		Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
		Destination fila = (Destination) context.lookup("financeiro");
		
		QueueBrowser queueBrowser = session.createBrowser((Queue) fila);

		Enumeration enumeration = queueBrowser.getEnumeration();
		
		while(enumeration.hasMoreElements()){
			ObjectMessage objectMessage = (ObjectMessage) enumeration.nextElement();
			System.out.println("Mensagem: " + objectMessage.getObject());
		}
		
		MessageConsumer consumer = session.createConsumer(fila);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message mensagem) {

				ObjectMessage objectMessage = (ObjectMessage) mensagem;
		        try {
					System.out.println(objectMessage.getObject());
//					objectMessage.acknowledge();
					session.commit();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
		});
		
		
		
		
		new Scanner(System.in).nextLine();
		
		connection.close();
		context.close();
		
		
	}
}
