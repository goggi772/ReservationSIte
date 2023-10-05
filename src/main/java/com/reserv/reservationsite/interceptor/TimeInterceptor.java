package com.reserv.reservationsite.interceptor;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

@Component
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        int day = dateTime.getDayOfWeek().getValue();
        int hour = dateTime.getHour();
        int min = dateTime.getMinute();
        int sec = dateTime.getSecond();

           //월 화 수 목 금
        if (day < 6 && (hour == 8 || (hour == 9 && (min != 0 || sec != 0)) || //8시 ~ 9시 59분
                ((hour == 18 && min >= 20 && (min != 20 || sec != 0)) || (hour == 19 && min < 20)) ||  //6시20분 ~ 7시20분
                ((hour == 19 && (min != 20 || sec != 0)) || (hour == 20 && min < 20)))) {  //7시20분 ~ 8시20분에만 예약 가능함
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
    }

}
