package com.otterdesk.pdfWorkerService.service;

import com.otterdesk.pdfWorkerService.config.RabbitConfiguration;
import com.otterdesk.pdfWorkerService.model.PdfEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
/*
This is sender file which uses beans from RabbitConfiguration.class

 */

@Component
public class PdfEventSender {

    private static final Logger logger = LoggerFactory.getLogger(PdfEventSender.class.getName());

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Queue queue;

    public  void send(PdfEvent pdfEvent) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfiguration.class);
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
        amqpTemplate.convertAndSend("PdfEventQueue", pdfEvent);
        //Thread.sleep(5000);
        logger.info("Sent PDF Event to RabbiMQ :" + pdfEvent.getFileLocation() + "'");
    }
}
