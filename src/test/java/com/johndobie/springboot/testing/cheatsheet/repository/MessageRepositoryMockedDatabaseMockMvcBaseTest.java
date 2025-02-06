package com.johndobie.springboot.testing.cheatsheet.repository;

import com.johndobie.springboot.testing.cheatsheet.model.Message;
import com.johndobie.springboot.testing.cheatsheet.util.MockMvcBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MessageRepositoryMockedDatabaseMockMvcBaseTest extends MockMvcBaseTest {

    @MockitoBean
    private MessageRepository messageRepository;

    @Test
    public void testSaveAndFindById() {
        Message message = new Message();
        message.setContent("Test message");

        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> {
            Message savedMessage = invocation.getArgument(0);
            savedMessage.setId(1L);
            return savedMessage;
        });

        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));

        Message savedMessage = messageRepository.save(message);
        Optional<Message> retrievedMessage = messageRepository.findById(savedMessage.getId());

        assertThat(retrievedMessage).isPresent();
        assertThat(retrievedMessage.get().getContent()).isEqualTo("Test message");
    }
}
