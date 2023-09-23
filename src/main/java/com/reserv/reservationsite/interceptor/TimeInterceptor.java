package com.reserv.reservationsite.interceptor;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

@Component
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // 현재 시간
        int day = calendar.get(Calendar.DAY_OF_WEEK);  //요일
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 시간
        int min = calendar.get(Calendar.MINUTE); // 분
        int sec = calendar.get(Calendar.SECOND); // 초

           //월 화 수 목 금
        if ((day > 1 && day < 7) && (hour == 8 || (hour == 9 && (min != 0 || sec != 0)) || //8시 ~ 9시 59분
                ((hour == 18 && min >= 20 && (min != 20 || sec != 0)) || (hour == 19 && min < 20)) ||  //6시20분 ~ 7시20분
                ((hour == 19 && (min != 20 || sec != 0)) || (hour == 20 && min < 20)))) {  //7시20분 ~ 8시20분에만 예약 가능함
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return true;
        }
    }

}
