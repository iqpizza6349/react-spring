package com.example.reactspring.service.user;

import com.example.reactspring.advice.exception.CUserNotFoundException;
import com.example.reactspring.entity.user.dto.UserRequestDto;
import com.example.reactspring.entity.user.dto.UserResponseDto;
import com.example.reactspring.entity.user.User;
import com.example.reactspring.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    @Transactional
    public Long save(UserRequestDto userRequestDto) {
        return userRepository.save(userRequestDto.toEntity()).getAutoId();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, UserRequestDto userRequestDto) {
        User modifyUser = userRepository
                .findById(id).orElseThrow(CUserNotFoundException::new);
        modifyUser.updateNickname(userRequestDto.getNickname());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
