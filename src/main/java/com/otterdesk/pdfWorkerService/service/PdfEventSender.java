package com.otterdesk.pdfWorkerService.service;

import com.otterdesk.pdfWorkerService.model.PdfEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class PdfEventSender {

    private static final Logger logger = LoggerFactory.getLogger(PdfEventSender.class.getName());

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Queue queue;

    public  void send(PdfEvent pdfEvent) {
        this.rabbitTemplate.convertAndSend(queue.getName(), pdfEvent);
        logger.info("Sent PDF Event to RabbiMQ :" + pdfEvent.getFileLocation() + "'");
    }
}
