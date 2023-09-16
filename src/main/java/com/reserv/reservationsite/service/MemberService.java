package com.reserv.reservationsite.service;

import com.reserv.reservationsite.DTO.MemberDTO;
import com.reserv.reservationsite.DTO.RegisterDTO;
import com.reserv.reservationsite.DTO.TokenInfo;
import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.repository.MemberRepository;
import com.reserv.reservationsite.exception.ErrorCode;
import com.reserv.reservationsite.exception.ErrorResponse;
import com.reserv.reservationsite.exception.NotEqualsPasswordException;
import com.reserv.reservationsite.exception.NotFoundUserException;
import com.reserv.reservationsite.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenInfo login(String username, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return jwtTokenProvider.generateToken(authentication);
    }

    @Transactional
    public ResponseEntity<ErrorResponse> register(RegisterDTO dto) {
        if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
            System.out.println("존재함");
            throw new NotFoundUserException(ErrorCode.ALREADY_EXIST_USERNAME);
        } else {
            System.out.println("존재하지 않음");
            dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            memberRepository.save(dto.toEntity());
            return ErrorResponse.toResponseEntity(ErrorCode.STATUS_OK);
        }
    }

    /*@Transactional
    public void modifying(String name, String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundUserException(ErrorCode.NOT_EXIST_USER));
        member.modi_username(name);
        memberRepository.save(member);
    }*/

    @Transactional
    public ResponseEntity<ErrorResponse> change_password(String username, String oldPassword, String newPassword) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundUserException(ErrorCode.NOT_EXIST_USER));
        if (bCryptPasswordEncoder.matches(oldPassword, member.getPassword())) {
            member.reset_change_pass(bCryptPasswordEncoder.encode(newPassword));
            memberRepository.save(member);
            return ErrorResponse.toResponseEntity(ErrorCode.STATUS_OK);  //비밀번호 변경 성공
        } else throw new NotEqualsPasswordException(ErrorCode.INCORRECT_ID_PASSWORD);  //비밀번호 일치하지 않음
    }

    @Transactional
    public ResponseEntity<ErrorResponse> reset_password(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundUserException(ErrorCode.NOT_EXIST_USER));
        member.reset_change_pass(bCryptPasswordEncoder.encode("0000"));
        memberRepository.save(member);
        return ErrorResponse.toResponseEntity(ErrorCode.STATUS_OK);
    }

    public MemberDTO findByMember(String username) {
        return memberRepository.findByUsername(username).map(MemberDTO::new).orElseThrow(() ->
                new NotFoundUserException(ErrorCode.NOT_EXIST_USER));
    }

    public Page<MemberDTO> get_member_info(Pageable pageable) {
        return memberRepository.findAll(pageable).map(MemberDTO::new);
    }



}

