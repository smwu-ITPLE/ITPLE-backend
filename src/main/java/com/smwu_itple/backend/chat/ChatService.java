package com.smwu_itple.backend.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;
    public Chat saveChat(Chat chat){
        return chatRepository.save(chat);
    }

    public Optional<Chat> getChatById(Long id){
        return chatRepository.findById(id);
    }

}
