package com.otterdesk.pdfWorkerService.web;

import com.otterdesk.pdfWorkerService.config.RabbitConfiguration;
import com.otterdesk.pdfWorkerService.model.PdfEvent;
import com.otterdesk.pdfWorkerService.service.PdfEventSender;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pdf")
public class PdfEventSenderController {
    @Autowired
    private RabbitTemplate amqpTemplate;

    @Autowired
    private Queue rabbitQueue;

    @Autowired
    PdfEventSender pdfEventSender;

    @ModelAttribute("event")
    public PdfEvent newPdfEvent() {
        return new PdfEvent();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/event/send")
    public String process(@RequestBody PdfEvent pdfEvent) {
        pdfEventSender.send(pdfEvent);
        return "Sent to RQ ";
    }
}
