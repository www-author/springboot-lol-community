package com.lol.community.user.service;

import com.lol.community.user.domain.User;
import com.lol.community.user.repository.UserRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;

  @Override
  public User getUser(Long user_id){
    return userRepository.findById(user_id).orElseThrow(()->new NoSuchElementException("없는 유저입니다."));
  }
}
