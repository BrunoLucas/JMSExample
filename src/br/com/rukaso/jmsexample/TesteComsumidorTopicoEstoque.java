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
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteComsumidorTopicoEstoque {

	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
		
		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection("user", "senha");
		connection.setClientID("estoque");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topico = (Topic) context.lookup("loja");
		
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");
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
