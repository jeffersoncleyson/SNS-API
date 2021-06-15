package br.com.sensedia.service.notification;

import br.com.sensedia.model.MessageModel;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SQSService {

    private final Logger logger = LoggerFactory.getLogger(SQSService.class);

    @Autowired
    private AmazonSQSClient amazonSQSClient;

    @Value("${sensedia.application.sqs.queue-url}")
    private String queue_url;

    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private void deleteMessage(Message messageObject) {

        final String messageReceiptHandle = messageObject.getReceiptHandle();
        amazonSQSClient.deleteMessage(new DeleteMessageRequest(queue_url, messageReceiptHandle));

    }

    public List<MessageModel> receiveMessages(){

        List<MessageModel> listParseMessages = new ArrayList<>();

        final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queue_url)
                .withMaxNumberOfMessages(2)
                .withWaitTimeSeconds(3);

        final List<Message> messages = amazonSQSClient.receiveMessage(receiveMessageRequest).getMessages();

        for (Message messageObject : messages) {
            String message = messageObject.getBody();
            try {
                MessageModel mModel = objectMapper.readValue(messageObject.getBody(), MessageModel.class);
                listParseMessages.add(mModel);
                deleteMessage(messageObject);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return listParseMessages;
    }



}
