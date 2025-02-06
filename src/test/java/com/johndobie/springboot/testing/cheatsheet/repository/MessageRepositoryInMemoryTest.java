package com.johndobie.springboot.testing.cheatsheet.repository;

import com.johndobie.springboot.testing.cheatsheet.model.Message;
import com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper.HELLO_WORLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class MessageRepositoryInMemoryTest {

    public static final String UPDATED_CONTENT = "Updated content";
    public static final String CONTENT_1 = "Content 1";
    public static final String RANDOM_MESSAGE_1 = "Random message 1";
    
    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void testSaveAndFindMessageById() {
        Message message = TestDataHelper.getMessage(HELLO_WORLD);
        Message savedMessage = messageRepository.save(message);

        Optional<Message> retrievedMessage = messageRepository.findById(savedMessage.getId());
        assertThat(retrievedMessage).isPresent();
        assertThat(retrievedMessage.get().getContent()).isEqualTo(message.getContent());
    }

    @Test
    public void testFindAllMessages() {
        List<Message> retrievedMessages = messageRepository.findAll();
        assertThat(retrievedMessages.size()).isEqualTo(5);
        assertThat(retrievedMessages).anyMatch(message -> RANDOM_MESSAGE_1.equals(message.getContent()));
    }

    @Test
    public void testFindNoMessages() {
        messageRepository.deleteAll();
        List<Message> retrievedMessages = messageRepository.findAll();
        assertThat(retrievedMessages).isEmpty();
    }

    @Test
    public void testUpdateMessage() {
        Message message = TestDataHelper.getMessage(HELLO_WORLD);
        Message savedMessage = messageRepository.save(message);

        savedMessage.setContent(UPDATED_CONTENT);

        Message updatedMessage = messageRepository.save(savedMessage);

        Optional<Message> retrievedMessage = messageRepository.findById(updatedMessage.getId());
        assertThat(retrievedMessage).isPresent();
        assertThat(retrievedMessage.get().getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    public void testDeleteMessage() {
        Message message = TestDataHelper.getMessage(HELLO_WORLD);
        Message savedMessage = messageRepository.save(message);

        messageRepository.deleteById(savedMessage.getId());

        Optional<Message> retrievedMessage = messageRepository.findById(savedMessage.getId());
        assertThat(retrievedMessage).isNotPresent();
    }

    @Test
    public void testSaveMessageWithNullContent() {
        Message message = new Message();
        message.setContent(null);

        assertThatThrownBy(() -> messageRepository.save(message))
                .isInstanceOf(Exception.class);
    }

    @Test
    public void testFindMessageByContent() {
        Message message1 = TestDataHelper.getMessage(CONTENT_1);
        messageRepository.save(message1);

        Optional<Message> retrievedMessage = messageRepository.findByContent(CONTENT_1);
        assertThat(retrievedMessage).isPresent();
        assertThat(retrievedMessage.get().getContent()).isEqualTo(CONTENT_1);
    }

}