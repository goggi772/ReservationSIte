package com.reserv.reservationsite.service;

import com.reserv.reservationsite.DTO.MemberDTO;
import com.reserv.reservationsite.DTO.RegisterDTO;
import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.repository.MemberRepository;
import com.reserv.reservationsite.exception.ErrorCode;
import com.reserv.reservationsite.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional
    public void modifying(String name, String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundUserException(ErrorCode.NOT_EXIST_USER));
        member.modi_username(name);
        memberRepository.save(member);
    }

    @Transactional
    public void reset_password(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundUserException(ErrorCode.NOT_EXIST_USER));
        member.reset_pass(bCryptPasswordEncoder.encode("0000"));
        memberRepository.save(member);
    }




}

