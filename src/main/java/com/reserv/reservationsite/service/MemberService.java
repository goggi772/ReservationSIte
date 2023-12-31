package com.reserv.reservationsite.service;

import com.reserv.reservationsite.DTO.MemberDTO;
import com.reserv.reservationsite.DTO.RegisterDTO;
import com.reserv.reservationsite.DTO.TokenInfo;
import com.reserv.reservationsite.core.entity.Member;
import com.reserv.reservationsite.core.entity.RefreshToken;
import com.reserv.reservationsite.core.repository.MemberRepository;
import com.reserv.reservationsite.core.redisRepository.RefreshTokenRepository;
import com.reserv.reservationsite.exception.*;
import com.reserv.reservationsite.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, Object> redisTemplate;

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
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, true);
        refreshTokenRepository.save(RefreshToken.builder()
                .username(username).
                refreshToken(tokenInfo.getRefreshToken())
                .timeToLive(Duration.ofHours(1).toMillis()).build());  //redis에 1시간동안 저장

        return tokenInfo;

    }

    public TokenInfo regenerationAccessToken(TokenInfo tokenInfo) {
        String access = tokenInfo.getAccessToken();
        String refresh = tokenInfo.getRefreshToken();

        if (refresh != null && jwtTokenProvider.validateToken(refresh)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(access);
            RefreshToken redisgetrefreshToken = refreshTokenRepository.findById(authentication.getName()).orElseThrow(() ->
                    new JwtTokenException(ErrorCode.TOKEN_ERROR.getDetail(), ErrorCode.TOKEN_ERROR));
            if (redisgetrefreshToken.getRefreshToken().equals(refresh)) {
                return jwtTokenProvider.generateToken(authentication, false);
            } else throw new JwtTokenException(ErrorCode.TOKEN_ERROR.getDetail(), ErrorCode.TOKEN_ERROR);
        } else throw new JwtTokenException(ErrorCode.TOKEN_ERROR.getDetail(), ErrorCode.TOKEN_ERROR);
    }

    @Transactional
    public ResponseEntity<ErrorResponse> register(RegisterDTO dto) {
        if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new NotFoundUserException(ErrorCode.ALREADY_EXIST_USERNAME);
        } else {
            dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            memberRepository.save(dto.toEntity());
            return ErrorResponse.toResponseEntity(ErrorCode.STATUS_OK);
        }
    }
    @Transactional
    public ResponseEntity<ErrorResponse> registerAdmin(RegisterDTO dto) {
        if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new NotFoundUserException(ErrorCode.ALREADY_EXIST_USERNAME);
        } else {
            dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            memberRepository.save(dto.toEntityAdmin());
            return ErrorResponse.toResponseEntity(ErrorCode.STATUS_OK);
        }
    }

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

    @Transactional
    public void delete_user_info(String username) {
        memberRepository.deleteByUsername(username);
    }

    public Member findByMember(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundUserException(ErrorCode.NOT_EXIST_USER));
    }

    public List<MemberDTO> get_member_info() {
        return memberRepository.findAll(Sort.by(Sort.Direction.ASC, "username")).stream().map(MemberDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<ErrorResponse> modify_member_date(MemberDTO dto) {
        Member member = memberRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
                new NotFoundUserException(ErrorCode.NOT_EXIST_USER));
        member.set_startDate_endDate(dto.getStartDate(), dto.getEndDate());
        memberRepository.save(member);

        return ErrorResponse.toResponseEntity(ErrorCode.STATUS_OK);
    }




}

