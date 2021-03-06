package br.com.rukaso.jmsexample.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.rukaso.jmsexample.modelo.Pedido;
import br.com.rukaso.jmsexample.modelo.PedidoFactory;

public class TestProducerQueue {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection("user", "senha");
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("financeiro");

		MessageProducer producer = session.createProducer(fila);
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		
		for(int i = 0; i < 2000; i++){
			Message mensagem = session.createObjectMessage(pedido);
			producer.send(mensagem);
		}
		
		connection.close();
		context.close();

	}
}
