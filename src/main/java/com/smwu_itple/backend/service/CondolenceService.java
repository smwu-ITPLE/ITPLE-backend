package com.smwu_itple.backend.service;

import com.smwu_itple.backend.domain.*;
import com.smwu_itple.backend.dto.request.MessageCreateRequest;
import com.smwu_itple.backend.dto.request.PayCreateRequest;
import com.smwu_itple.backend.dto.response.MessageCreateResponse;
import com.smwu_itple.backend.dto.response.PayCreateResponse;
import com.smwu_itple.backend.dto.response.PaySumResponse;
import com.smwu_itple.backend.repository.MessageRepository;
import com.smwu_itple.backend.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CondolenceService {
    private final MessageRepository messageRepository;
    private final PayRepository payRepository;

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
        message.setLate(late);
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
                sender.getName(),
                receiver.getName(),
                message.getContent(),
                message.getAttachment(),
                message.getCreatedAt()
        );
    }

    @Transactional
    public PayCreateResponse createPay(Long lateId, Long senderId, PayCreateRequest payCreateRequest) throws IOException {
        Late late = lateService.findLateByIdOrThrow(lateId);
        User sender = userService.findUserByIdOrThrow(senderId);
        Owner receiver = ownerService.findOwnerByIdOrThrow(payCreateRequest.getReceiverId());

        Pay pay = new Pay();
        pay.setLate(late);
        pay.setSender(sender);
        pay.setReceiver(receiver);
        pay.setEnvelope(payCreateRequest.getEnvelope());
        pay.setAmount(payCreateRequest.getAmount());
        pay.setCreatedAt(LocalDateTime.now());

        payRepository.save(pay);
        return new PayCreateResponse(
                sender.getName(),
                receiver.getName(),
                pay.getEnvelope(),
                pay.getAmount(),
                pay.getCreatedAt()
        );
    }

    public List<MessageCreateResponse> getMessageList(Long lateId) {
        Late late = lateService.findLateByIdOrThrow(lateId);
        List<Message> messages = messageRepository.findByLate(late);

        return messages.stream()
                .map(message -> new MessageCreateResponse(
                        message.getSender().getName(),
                        message.getReceiver().getName(),
                        message.getContent(),
                        message.getAttachment(),
                        message.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public List<PayCreateResponse> getPayList(Long lateId) {
        Late late = lateService.findLateByIdOrThrow(lateId);
        List<Pay> pays = payRepository.findByLate(late);

        return pays.stream()
                .map(pay -> new PayCreateResponse(
                        pay.getSender().getName(),
                        pay.getReceiver().getName(),
                        pay.getEnvelope(),
                        pay.getAmount(),
                        pay.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public List<PaySumResponse> getPaySum(Long lateId){
        Late late = lateService.findLateByIdOrThrow(lateId);
        List<Pay> pays = payRepository.findByLate(late);

        //전체 부의금
        double totalAmount = pays.stream()
                .mapToDouble(Pay::getAmount)
                .sum();

        Map<Owner, Integer> ownerTotalMap = pays.stream()
                .collect(Collectors.groupingBy(
                        Pay::getReceiver,
                        Collectors.summingInt(Pay::getAmount)
                ));

        return ownerTotalMap.entrySet().stream()
                .map(entry -> {
                    Owner owner = entry.getKey();
                    Integer ownerTotal = entry.getValue();
                    double percentage = totalAmount > 0 ? (ownerTotal / totalAmount) * 100 : 0; // 퍼센트 계산
                    return new PaySumResponse(
                            owner.getUser().getName(),
                            ownerTotal,
                            percentage
                    );
                })
                .collect(Collectors.toList());
    }
}
