package com.fithub.service.member;

import com.fithub.config.SecurityContextGenerator;
import com.fithub.dto.base.AttachmentDTO;
import com.fithub.dto.member.MemberDTO;
import com.fithub.dto.user.UserDTO;
import com.fithub.exception.DuplicateException;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.base.Attachment;
import com.fithub.model.member.Member;
import com.fithub.model.member.MemberStatus;
import com.fithub.model.subscription.Subscription;
import com.fithub.model.user.User;
import com.fithub.repository.member.MemberRepository;
import com.fithub.service.base.NotificationService;
import com.fithub.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final ModelMapper mapper;

    private final Logger logger = Logger.getLogger(MemberServiceImpl.class.getName());

    @Autowired
    private SecurityContextGenerator securityContextGenerator;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, UserService userService, NotificationService notificationService) {
        this.memberRepository = memberRepository;
        this.userService = userService;
        this.notificationService = notificationService;
        this.mapper = new ModelMapper();
    }

    // Get All Members
    public List<MemberDTO> getMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(member -> mapper.map(member, MemberDTO.class)).toList();
    }

    @Override
    public MemberDTO getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        return mapper.map(member, MemberDTO.class);
    }

    // Add New Member
    public MemberDTO addMember(MemberDTO membershipDTO) {
        Member memberById = memberRepository.findByIdentificationNumber(membershipDTO.getIdentificationNumber());
        Member memberByEmail = memberRepository.findByEmail(membershipDTO.getEmail());
        if(memberById != null){
            throw new DuplicateException("Member with identification number " + membershipDTO.getIdentificationNumber() + " already exists!");
        }
        if(memberByEmail != null){
            throw new DuplicateException("Member with email " + membershipDTO.getEmail() + " already exists!");
        }
        Member member = memberRepository.save(mapper.map(membershipDTO, Member.class));
        return mapper.map(member, MemberDTO.class);
    }

    // Update Member
    public MemberDTO updateMember(Long id, MemberDTO membershipDTO) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        if(member.getId() > 0) {
            if(membershipDTO.getIdentificationNumber() != null && !membershipDTO.getIdentificationNumber().isEmpty()){
                member.setIdentificationNumber(membershipDTO.getIdentificationNumber());
            }
            if(membershipDTO.getFirstName() != null && !membershipDTO.getFirstName().isEmpty()){
                member.setFirstName(membershipDTO.getFirstName());
            }
            if(membershipDTO.getLastName() != null && !membershipDTO.getLastName().isEmpty()){
                member.setLastName(membershipDTO.getLastName());
            }
            if(membershipDTO.getEmail() != null && !membershipDTO.getEmail().isEmpty()){
                member.setEmail(membershipDTO.getEmail());
            }
            if(membershipDTO.getPhone() != null && !membershipDTO.getPhone().isEmpty()){
                member.setPhone(membershipDTO.getPhone());
            }
            if(membershipDTO.getGender() != null){
                member.setGender(membershipDTO.getGender());
            }

            memberRepository.save(member);
            return mapper.map(member, MemberDTO.class);
        } else {
            throw new ResourceNotFoundException("Member not found");
        }
    }

    // Delete Member
    public Map<String, String> deleteMember(Long id) {
        Map<String, String> response = new HashMap<>();
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        memberRepository.delete(member);
        response.put("message", "Resource has been successfully deleted");
        response.put("status", "success");
        return  response;
    }

    // Search Members
    public List<MemberDTO> searchMembers(String keyword) {
        MemberStatus status;
        try {
            status = MemberStatus.valueOf(keyword.toUpperCase());
        } catch (IllegalArgumentException e) {
            status = null;
        }
        List<Member> members = memberRepository.searchByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingOrIdentificationNumberContainingOrStatusEquals(keyword, keyword, keyword, keyword, status);
        return members.stream().map(member -> mapper.map(member, MemberDTO.class)).toList();
    }

    // Get My Member Profile
    public MemberDTO getMyProfile(String token) {
        UserDTO userDTO = userService.getUserByToken(token.substring(7));
        Member member = memberRepository.findByEmail(userDTO.getEmail());
        if (member != null) {
            member.setUser(mapper.map(userDTO, User.class));
            return mapper.map(member, MemberDTO.class);
        }
        else {
            throw new ResourceNotFoundException("Member not found");}
    }

    @Override
    public MemberDTO uploadProfilePicture(Long id, AttachmentDTO picture) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            Attachment attachment = mapper.map(picture, Attachment.class);
            member.setProfilePicture(attachment);
            try {
                if(member.getUser() == null){
                    member.setUser(mapper.map(userService.getUserByEmail(member.getEmail()), User.class));
                }
            } catch (Exception e) {
                // Do nothing;
            }
            memberRepository.save(member);
            return mapper.map(member, MemberDTO.class);
        }
        else {
            throw new ResourceNotFoundException("Member not found");
        }
    }

    @Override
    public Map<String, String> deleteProfilePicture(Long id) {
        Map<String, String> response = new HashMap<>();
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found"));
        member.setProfilePicture(null);
        memberRepository.save(member);
        response.put("message", "Profile picture has been successfully deleted");
        response.put("status", "success");
        return  response;
    }

    @Override
    public MemberDTO findMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            return mapper.map(member, MemberDTO.class);
        }
        else {
            return null;
        }
    }

    // Crons

    public void checkMemberStatus(SecurityContext context) {
        logger.info("Checking member status");
        List<Member> members = memberRepository.findAll();
        for(Member member: members){
            UserDTO userDTO = userService.getUserByEmail(member.getEmail());
            List<Subscription> subscriptions = member.getSubscriptions().stream().toList();
            if(!subscriptions.isEmpty()){
                List<Subscription> currentSubscriptions = subscriptions.stream().filter(subscription -> subscription.getEndDate().after(new Date(System.currentTimeMillis()))).toList();
                if(!currentSubscriptions.isEmpty()){
                    // Expiring
                    Subscription currentSubscription = currentSubscriptions.stream().max((s1, s2) -> s1.getEndDate().compareTo(s2.getEndDate())).orElseThrow();
                    if(currentSubscription.getEndDate().before(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7 * 2))){
                        member.setStatus(MemberStatus.EXPIRING);
                        // Send Notification
                        String title = "Dear " + member.getFirstName() + " " + member.getLastName() + ", your subscription" + currentSubscription.getReference() + " is expiring soon";
                        String message = "Our valued member, your subscription " + currentSubscription.getReference() + " is expiring on " + currentSubscription.getEndDate();
                        notificationService.sendNotification(userDTO, title, message);
                    } else {
                        member.setStatus(MemberStatus.ACTIVE);
                    }
                } else {
                    member.setStatus(MemberStatus.EXPIRED);
                    // Send Notification
                    String title = "Dear " + member.getFirstName() + " " + member.getLastName() + ", your subscription has expired";
                    String message = "Our valued member, your subscription has expired. Please renew your subscription to continue enjoying our services";
                    notificationService.sendNotification(userDTO, title, message);
                }
            } else {
                member.setStatus(MemberStatus.NEW);
                // Send Notification
                String title = "Welcome " + member.getFirstName() + " " + member.getLastName();
                String message = "Our valued member, welcome to our platform. Please subscribe to enjoy our services";
                notificationService.sendNotification(userDTO, title, message);
            }
            memberRepository.save(member);
        }
    }

    public void linkMemberToUSer(SecurityContext context) {
        logger.info("Linking member to user");
        List<Member> members = memberRepository.findAll();
        for(Member member: members){
            try {
                UserDTO userDTO = userService.getUserByEmail(member.getEmail());
                if(userDTO != null){
                    member.setUser(mapper.map(userDTO, User.class));
                    memberRepository.save(member);
                }
            } catch (ResourceNotFoundException e) {
                continue;
            }
        }
    }

    @Transactional
    @Scheduled(fixedRate = 1000 * 60 * 30) // Adjust the fixedRate as needed
    public void performTask() {
        SecurityContext context = securityContextGenerator.createSecurityContext();
        try {
            checkMemberStatus(context);
            linkMemberToUSer(context);
        } finally {
            //context.setAuthentication(null);
        }
    }

}
