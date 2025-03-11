package com.tests.test_case_helper.service.security.detail;

import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.repository.UserRepository;
import com.tests.test_case_helper.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByLogin(username);

        return user.map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
