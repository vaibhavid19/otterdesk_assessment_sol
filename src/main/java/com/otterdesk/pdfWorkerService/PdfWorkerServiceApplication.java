package com.otterdesk.pdfWorkerService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otterdesk.pdfWorkerService.config.RabbitConfiguration;
import com.otterdesk.pdfWorkerService.model.PdfEvent;
import com.otterdesk.pdfWorkerService.service.PdfEventSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ErrorHandler;

/* This is a listener piece. This file along with RabbitConfiguration.class in another project will act as
listener on another port and is able to listen to the message sent by PdfEventSenderController
 */
@SpringBootApplication
public class PdfWorkerServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(PdfEventSender.class.getName());

	public static void main(String[] args) {

		SpringApplication.run(PdfWorkerServiceApplication.class, args);

		final ApplicationContext rabbitConfig = new AnnotationConfigApplicationContext(RabbitConfiguration.class);
		final ConnectionFactory rabbitConnectionFactory = rabbitConfig.getBean(ConnectionFactory.class);
		final Queue rabbitQueue = rabbitConfig.getBean(Queue.class);

		final SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
		listenerContainer.setConnectionFactory(rabbitConnectionFactory);
		listenerContainer.setQueueNames(rabbitQueue.getName());

		listenerContainer.setMessageListener(new MessageListener() {
			public void onMessage(Message message)  {
				byte[] pdfEventBody = message.getBody();

				String eventString = new String(pdfEventBody);
				PdfEvent pdfEventFromQueue = null;
				try {
					pdfEventFromQueue = new ObjectMapper().readValue(eventString, PdfEvent.class);
					logger.info("Received PDF Event from RabbiMQ :" + pdfEventFromQueue.getFileLocation() + "'");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		listenerContainer.setErrorHandler(new ErrorHandler() {
			public void handleError(Throwable t) {
				t.printStackTrace();
			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Shutting down listener");
				listenerContainer.shutdown();
			}
		});

		listenerContainer.start();
		System.out.println("listening from the Queue...");

	}

}

