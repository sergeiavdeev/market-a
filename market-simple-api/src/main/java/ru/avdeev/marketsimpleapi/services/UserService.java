package ru.avdeev.marketsimpleapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.entities.User;
import ru.avdeev.marketsimpleapi.repository.RoleRepository;
import ru.avdeev.marketsimpleapi.repository.UserRepository;

@Service
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder encoder;


    @Transactional
    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsernameAndIsBlocked(username, false)
                .flatMap(user -> roleRepository.findByUserId(user.getId())
                        .collectList()
                        .flatMap(roles -> {
                            user.setRoles(roles);
                            return Mono.just(user);
                        }));
    }

    public Mono<User> register(User user) {
        return userRepository.save(user);
    }

    public Mono<Boolean> checkPassword(String passwordForCheck, String password) {
        return Mono.just(encoder.matches(passwordForCheck, password));
    }

    @Autowired
    public void init(UserRepository repository, RoleRepository roleRepository, PasswordEncoder pe) {
        this.userRepository = repository;
        this.roleRepository = roleRepository;
        this.encoder = pe;
    }
}
