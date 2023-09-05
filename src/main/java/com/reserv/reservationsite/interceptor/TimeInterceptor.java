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

           //월 화 수 목 금
        if ((day > 1 && day < 7) && (((hour == 8 && min >= 10) || (hour == 9 && min < 5)) ||  //8시10분 ~ 9시5분
                ((hour == 9 && min >= 10) || hour == 10 && min < 5) ||  //9시10분 ~ 10시5분
                ((hour == 18 && min >= 30) || (hour == 19 && min < 25)) ||  //6시30분 ~ 7시25분
                ((hour == 19 && min >= 30) || (hour == 20 && min < 25)))) {  //7시30분 ~ 8시25분에만 예약 가능함
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
    }

}
