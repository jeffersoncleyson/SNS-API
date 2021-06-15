package br.com.sensedia.service.schedule;

import br.com.sensedia.model.MessageModel;
import br.com.sensedia.repository.MessageRepository;
import br.com.sensedia.service.mesageria.KafkaProducerService;
import br.com.sensedia.service.notification.SQSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SQSSchedule {

    private final Logger logger = LoggerFactory.getLogger(SQSSchedule.class);

    @Autowired
    private SQSService sqsService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private MessageRepository messageRepository;

    @Scheduled(fixedRateString = "${sensedia.application.sqs.pollingInSeconds}")
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        List<MessageModel> messages = sqsService.receiveMessages();
        messages.stream().forEach(m -> {
            MessageModel mSave = messageRepository.save(m);
            kafkaProducerService.sendMessage(mSave);
        });
    }
}
