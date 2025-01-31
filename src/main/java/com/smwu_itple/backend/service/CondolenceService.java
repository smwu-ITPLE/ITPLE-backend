package com.smwu_itple.backend.service;

import com.smwu_itple.backend.domain.*;
import com.smwu_itple.backend.dto.request.MessageCreateRequest;
import com.smwu_itple.backend.dto.response.MessageCreateResponse;
import com.smwu_itple.backend.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CondolenceService {
    private final MessageRepository messageRepository;

    private final LateService lateService;
    private final UserService userService;
    private final OwnerService ownerService;

    //조문 메시지 전송
    @Transactional
    public MessageCreateResponse createMessage(Long lateId, Long senderId, MultipartFile attachment, MessageCreateRequest messageCreateRequest) throws IOException {
        Late late = lateService.findLateByIdOrThrow(lateId);
        User sender = userService.findUserByIdOrThrow(senderId);
        Owner receiver = ownerService.findOwnerByIdOrThrow(messageCreateRequest.getReceiverId());

        String profilePath = lateService.saveProfileFile(attachment);

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setCreatedAt(LocalDateTime.now());
        message.setAttachment(profilePath);

        //내용이 있을 경우 저장
        if (messageCreateRequest.getContent() != null) {
            message.setContent(messageCreateRequest.getContent());
        }

        messageRepository.save(message);
        return new MessageCreateResponse(
                lateId,
                sender.getName(),
                receiver.getName(),
                message.getContent(),
                message.getAttachment(),
                message.getCreatedAt()
        );
    }
}
