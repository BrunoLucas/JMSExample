package br.com.rukaso.jmsexample.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
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

public class TestProducerQueueLog {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection("user", "senha");
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination fila = (Destination) context.lookup("LOG");

		MessageProducer producer = session.createProducer(fila);

		TextMessage mensagemLogInfo = session.createTextMessage("(INFO) | Teste Log INFO");
		TextMessage mensagemLogDebug = session.createTextMessage("(DEBUG) | Teste Log DEBUG");
		TextMessage mensagemLogWarn = session.createTextMessage("(WARN) | Teste Log WARN");
		TextMessage mensagemLogError = session.createTextMessage("(ERROR) | Teste Log ERROR");
		for(int i = 0; i < 20; i++){
			
			producer.send(mensagemLogInfo, DeliveryMode.NON_PERSISTENT, 0, 5000);
			producer.send(mensagemLogDebug, DeliveryMode.NON_PERSISTENT, 2, 5000);
			producer.send(mensagemLogWarn, DeliveryMode.NON_PERSISTENT, 4, 5000);
			producer.send(mensagemLogError, DeliveryMode.NON_PERSISTENT, 6, 5000);

		}
		
		connection.close();
		context.close();

	}
}
