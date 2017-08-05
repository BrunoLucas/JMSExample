package br.com.rukaso.jmsexample.jms;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXB;

import br.com.rukaso.jmsexample.modelo.Pedido;
import br.com.rukaso.jmsexample.modelo.PedidoFactory;

public class TesteProdutorTopico {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();

		ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = connectionFactory.createConnection("user", "senha");
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topico = (Destination) context.lookup("loja");

		MessageProducer producer = session.createProducer(topico);

		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		
//		StringWriter stringWriter = new StringWriter();
//		JAXB.marshal(pedido, stringWriter);
//		String xml = stringWriter.toString();
//		System.out.println(xml);
			
		
		for (int i = 0; i < 100; i++) {
			Message mensagem = null;
			if (i % 2 == 0) {
				mensagem = session.createObjectMessage(pedido);
			} else {
				mensagem = session.createObjectMessage(pedido);
			}
			mensagem.setBooleanProperty("ebook", false);
			producer.send(mensagem);
		}

		connection.close();
		context.close();

	}
}
