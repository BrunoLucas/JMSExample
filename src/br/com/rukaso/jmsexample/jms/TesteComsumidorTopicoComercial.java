package br.com.rukaso.jmsexample.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.rukaso.jmsexample.modelo.Pedido;

public class TesteComsumidorTopicoComercial {

	public static void main(String[] args) throws NamingException, JMSException {
	    System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","java.lang,sun.util,java.math,java.util,br.com.rukaso.jmsexample.modelo");
		
		InitialContext context = new InitialContext();
		
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection("user", "senha");
		connection.setClientID("comercial");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topico = (Topic) context.lookup("loja");
		
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message mensagem) {
				ObjectMessage objectMessage = (ObjectMessage) mensagem;
		        try {
					System.out.println((Pedido)objectMessage.getObject());
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
