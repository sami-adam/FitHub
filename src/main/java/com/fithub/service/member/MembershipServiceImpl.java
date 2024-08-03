package com.fithub.service.member;

import com.fithub.dto.member.MembershipDTO;
import com.fithub.model.member.Membership;
import com.fithub.repository.member.MembershipRepository;
import com.fithub.repository.product.ProductCategoryRepository;
import com.fithub.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService{
    private final MembershipRepository membershipRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<MembershipDTO> getMemberships() {
        return membershipRepository.findAll().stream()
                .map(membership -> mapper.map(membership, MembershipDTO.class))
                .toList();
    }

    @Override
    public MembershipDTO addMembership(MembershipDTO membershipDTO) {
        Membership membership = mapper.map(membershipDTO, Membership.class);
        membershipRepository.save(membership);
        return mapper.map(membership, MembershipDTO.class);
    }

    @Override
    public MembershipDTO updateMembership(Long id, MembershipDTO membershipDTO) {
        Membership membership = membershipRepository.findById(id).orElseThrow();
        if(membershipDTO.getFirstName() != null) {
            membership.setFirstName(membershipDTO.getFirstName());
        }
        if(membershipDTO.getLastName() != null) {
            membership.setLastName(membershipDTO.getLastName());
        }
        if(membershipDTO.getIdentificationNumber() != null) {
            membership.setIdentificationNumber(membershipDTO.getIdentificationNumber());
        }
        if(membershipDTO.getGender() != null) {
            membership.setGender(membershipDTO.getGender());
        }
        if(membershipDTO.getEmail() != null) {
            membership.setEmail(membershipDTO.getEmail());
        }
        if(membershipDTO.getPhone() != null) {
            membership.setPhone(membershipDTO.getPhone());
        }
        if(membershipDTO.getProductCategory() != null) {
            membership.setProductCategory(productCategoryRepository.findById(membershipDTO.getProductCategory().getId()).orElseThrow());
        }
        if(membershipDTO.getProduct() != null) {
            membership.setProduct(productRepository.findById(membershipDTO.getProduct().getId()).orElseThrow());
        }
        if(membershipDTO.getQuantity() != null) {
            membership.setQuantity(membershipDTO.getQuantity());
        }
        if(membershipDTO.getStartDate() != null) {
            membership.setStartDate(membershipDTO.getStartDate());
        }
        if(membershipDTO.getEndDate() != null) {
            membership.setEndDate(membershipDTO.getEndDate());
        }
        membershipRepository.save(membership);
        return mapper.map(membership, MembershipDTO.class);
    }

    @Override
    public Map<String, String> deleteMembership(Long id) {
        membershipRepository.deleteById(id);
        return Map.of("message", "Membership deleted successfully", "status", "success");
    }

    @Override
    public List<MembershipDTO> searchMemberships(String keyword) {
        return List.of();
    }
}
