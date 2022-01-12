package com.example.reactspring.service;

import com.example.reactspring.advice.exception.CUserNotFoundException;
import com.example.reactspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String autoId) throws UsernameNotFoundException {
        return userRepository.findById(Long.parseLong(autoId))
                .orElseThrow(CUserNotFoundException::new);
    }
}
