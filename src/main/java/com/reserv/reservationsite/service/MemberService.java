package com.reserv.reservationsite.service;

import com.reserv.reservationsite.DTO.RegisterDTO;
import com.reserv.reservationsite.core.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MemberRepository memberRepository;

    @Transactional
    public void register(RegisterDTO dto) {
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        memberRepository.save(dto.toEntity());
    }





}

