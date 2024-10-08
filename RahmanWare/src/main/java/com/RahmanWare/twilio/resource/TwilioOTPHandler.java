package com.RahmanWare.twilio.resource;

import com.RahmanWare.twilio.dto.PasswordResetRequestDto;
import com.RahmanWare.twilio.service.TwilioOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TwilioOTPHandler {

    @Autowired
    private TwilioOTPService service;

    public Mono<ServerResponse> sendOTP(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(PasswordResetRequestDto.class)
                .flatMap(dto -> service.sendOTPForPasswordReset(dto))
                .flatMap(responseDto -> ServerResponse.status(HttpStatus.OK)
                        .body(BodyInserters.fromValue(responseDto)))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BodyInserters.fromValue(e.getMessage())));
    }

    public Mono<ServerResponse> validateOTP(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(PasswordResetRequestDto.class)
                .flatMap(dto -> service.validateOTP(dto.getOneTimePassword(), dto.getUsername()))
                .flatMap(responseMessage -> ServerResponse.status(HttpStatus.OK)
                        .body(BodyInserters.fromValue(responseMessage)))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .body(BodyInserters.fromValue(e.getMessage())));
    }
}

