package com.example.ByteMonk.ProjectByteMonk.Filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//      log.info("Filter fired");
        long time= System.currentTimeMillis();

        Instant start= Instant.now();
        if(request.getRequestURI().toLowerCase().contains("/api")){

            try{
                filterChain.doFilter(request, response);
            }
            catch (Exception e)
            {
                log.error("Exception in filter chain: ", e);
                throw e;
            }
            finally {
                Instant end= Instant.now();
                long duration= System.currentTimeMillis()-time;


                log.info("\nIP Address:{}\n Method: {}\n Endpoint Accessed: {}\n Content Type: {}\n Status: {}  Timestamp: {}ms\n  Start-time: {}\n End-time: {}" , request.getRemoteAddr(), request.getMethod(), request.getRequestURI(),response.getContentType(), response.getStatus(), duration,
                        ZonedDateTime.ofInstant(start, ZoneId.systemDefault()),
                        ZonedDateTime.ofInstant(end, ZoneId.systemDefault()));
            }
        }

    }
}
