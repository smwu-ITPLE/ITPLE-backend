package com.smwu_itple.backend.service;

import com.smwu_itple.backend.domain.*;
import com.smwu_itple.backend.dto.ArchiveDto;
import com.smwu_itple.backend.dto.request.LateCreateRequest;
import com.smwu_itple.backend.dto.request.LateSearchRequest;
import com.smwu_itple.backend.dto.request.MessageCreateRequest;
import com.smwu_itple.backend.dto.request.PayCreateRequest;
import com.smwu_itple.backend.dto.response.*;
import com.smwu_itple.backend.dto.OwnerDto;
import com.smwu_itple.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LateService {
    private final LateRepository lateRepository;
    private final UserRepository userRepository;
    private final ArchiveRepository archiveRepository;
    private final MessageRepository messageRepository;
    private final PayRepository payRepository;

    private final UserService userService;
    private final OwnerService ownerService;

    // 랜덤 비밀번호 생성
    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        int password = 100000 + random.nextInt(900000); // 6자리 숫자 생성
        return String.valueOf(password);
    }

    //조문공간 생성
    @Transactional
    public LateCreateResponse createLate(MultipartFile profileFile, LateCreateRequest lateCreateRequest) throws IOException {

        String profilePath = saveProfileFile(profileFile);

        // Late 엔티티 생성
        Late late = new Late();
        late.setName(lateCreateRequest.getName());
        late.setProfile(profilePath);
        late.setAge(lateCreateRequest.getAge());
        late.setGender(lateCreateRequest.getGender());
        late.setDatePass(lateCreateRequest.getDatePass());
        late.setDateDeath(lateCreateRequest.getDateDeath());
        late.setLocation(lateCreateRequest.getLocation());
        late.setContent(lateCreateRequest.getContent());
        late.setPasswd(generateRandomPassword());

        // 상주 정보 설정
        List<Owner> owners = lateCreateRequest.getOwners().stream()
                .map(ownerRequest -> createOwner(ownerRequest, late))
                .collect(Collectors.toList());
        late.setOwners(owners);

        lateRepository.save(late);
        return new LateCreateResponse(late.getId(), late.getName(), late.getProfile());
    }


    // 프로필 파일 저장
    protected String saveProfileFile(MultipartFile profileFile) throws IOException {
        // 파일이 비어 있는지 확인
        if (profileFile == null || profileFile.isEmpty()) {
            return null; // 프로필 파일이 없으면 null 반환
        }

        String uploadDir = System.getProperty("user.dir") + "/uploads";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String uniqueFileName = UUID.randomUUID() + "_" + profileFile.getOriginalFilename();
        File savedFile = new File(directory, uniqueFileName);
        profileFile.transferTo(savedFile);

        return savedFile.getAbsolutePath();
    }

    //상주 생성
    private Owner createOwner(Owner ownerResponse, Late late) {
        User ownerUser = userRepository.findByPhonenumber(ownerResponse.getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("상주의 전화번호를 찾을 수 없습니다: " + ownerResponse.getPhoneNumber()));

        Owner owner = new Owner();
        owner.setName(ownerResponse.getName());
        owner.setRelation(ownerResponse.getRelation());
        owner.setPhoneNumber(ownerResponse.getPhoneNumber());
        owner.setUser(ownerUser);
        owner.setLate(late);

        return owner;
    }

    //조문공간 검색
    public Late searchLate(LateSearchRequest lateSearchRequest) {
        return lateRepository.findByNameAndPasswd(lateSearchRequest.getName(), lateSearchRequest.getPasswd())
                .orElseThrow(() -> new IllegalArgumentException("조문공간이 존재하지 않거나 비밀번호가 일치하지 않습니다."));
    }


    // 특정 조문공간 삭제
    @Transactional
    public void deleteLate(Long lateId) {
        Late late = findLateByIdOrThrow(lateId);
        // 삭제 수행
        lateRepository.deleteById(lateId);
    }

    //조문공간 조회
    public LateGetResponse findLate(Long lateId) {
        Late late = findLateByIdOrThrow(lateId);

        // Owner를 OwnerResponse로 변환
        List<OwnerDto> ownerDto = late.getOwners().stream()
                .map(owner -> new OwnerDto(
                        owner.getName(),
                        owner.getRelation(),
                        owner.getPhoneNumber()
                ))
                .collect(Collectors.toList());

        List<ArchiveDto> archiveDto = late.getArchives().stream()
                .map(archive -> new ArchiveDto(
                        archive.getNickname(),
                        archive.getContent()
                ))
                .collect(Collectors.toList());


        // Late를 LateGetResponse로 변환
        return new LateGetResponse(
                late.getName(),
                late.getProfile(),
                late.getAge(),
                late.getGender(),
                late.getDatePass(),
                late.getDateDeath(),
                late.getLocation(),
                ownerDto,
                archiveDto
        );
    }

    //조문공간 상주 조회
    public List<LateOwnerResponse> getLateOwner(Long lateId) {
        Late late = findLateByIdOrThrow(lateId);

        List<LateOwnerResponse> lateOwnerResponses = late.getOwners().stream()
                .map(owner -> new LateOwnerResponse(owner.getId(), owner.getName()))
                .collect(Collectors.toList());

        return lateOwnerResponses;
    }

    //조문 메시지 전송
    @Transactional
    public MessageCreateResponse createMessage(Long lateId, Long senderId, MultipartFile attachment, MessageCreateRequest messageCreateRequest) throws IOException {
        Late late = findLateByIdOrThrow(lateId);
        User sender = userService.findUserByIdOrThrow(senderId);
        Owner receiver = ownerService.findOwnerByIdOrThrow(messageCreateRequest.getReceiverId());

        String profilePath = saveProfileFile(attachment);

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

    //조문 부의금 송금
    @Transactional
    public PayCreateResponse createPay(Long lateId, Long senderId, PayCreateRequest payCreateRequest) throws IOException {
        Late late = findLateByIdOrThrow(lateId);
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

    //아카이브 작성
    @Transactional
    public ArchiveDto createArchive(Long lateId, ArchiveDto archiveRequest){
        Late late = findLateByIdOrThrow(lateId);
        Archive archive = new Archive();
        archive.setNickname(archiveRequest.getNickname());
        archive.setContent(archiveRequest.getContent());
        archive.setLate(late);

        archiveRepository.save(archive);
        return new ArchiveDto(
                archive.getNickname(),
                archive.getContent()
        );
    }

    public Late findLateByIdOrThrow(Long lateId) {
        return lateRepository.findById(lateId)
                .orElseThrow(() -> new IllegalArgumentException("조문공간이 존재하지 않습니다."));
    }
}
