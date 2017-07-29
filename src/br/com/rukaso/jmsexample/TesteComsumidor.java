package br.com.rukaso.jmsexample;

import java.util.Enumeration;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteComsumidor {

	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
		
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");
		
		QueueBrowser queueBrowser = session.createBrowser((Queue) fila);

		Enumeration enumeration = queueBrowser.getEnumeration();
		
		while(enumeration.hasMoreElements()){
			TextMessage textMessage = (TextMessage) enumeration.nextElement();
			System.out.println("Mensagem: " + textMessage.getText());
		}
		
		MessageConsumer consumer = session.createConsumer(fila);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message mensagem) {

				TextMessage textMessage  = (TextMessage)mensagem;
		        try {
					System.out.println(textMessage.getText());
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
