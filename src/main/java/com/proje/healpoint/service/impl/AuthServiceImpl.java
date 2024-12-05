package com.proje.healpoint.service.impl;

import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.jwt.AuthRequest;
import com.proje.healpoint.jwt.AuthResponse;
import com.proje.healpoint.jwt.JwtService;
import com.proje.healpoint.model.Patients;
import com.proje.healpoint.repository.PatientRepository;
import com.proje.healpoint.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PatientRepository patientRepository;


    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            UsernamePasswordAuthenticationToken auth=
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
            authenticationProvider.authenticate(auth);
            Optional<Patients> optionalPatient =patientRepository.findById(authRequest.getUsername());
            if(optionalPatient.isEmpty()){
                throw new BaseException
                        (new ErrorMessage(MessageType.NO_RECORD_EXIST,
                                " : "+authRequest.getUsername()));
            }
            String token = jwtService.generateToken(authRequest.getUsername());
            return new AuthResponse(token);
        }catch(Exception e){
            e.printStackTrace();
            throw new BaseException
                    (new ErrorMessage
                            (MessageType.AUTHENTICATION_FAILED,authRequest.getUsername()));
        }
    }
}
