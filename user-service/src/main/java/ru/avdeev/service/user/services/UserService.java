package ru.avdeev.service.user.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.avdeev.market.dto.PageDto;
import ru.avdeev.service.user.dto.UserDto;
import ru.avdeev.service.user.entity.UserEntity;
import ru.avdeev.service.user.exceptions.UserExistException;
import ru.avdeev.service.user.mappers.UserMapper;
import ru.avdeev.service.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final R2dbcEntityTemplate databaseClient;
    private final RoleService roleService;
    private final UserMapper mapper;

    @Value("${user.default.role}")
    private String defaultRole;

    public Mono<UserEntity> findByUsername(String username) {

        return userRepository.findByUsernameAndIsBlocked(username, false);
    }

    @Transactional
    public Mono<UserDto> register(UserEntity user) {
        return userRepository.findByUsername(user.getUsername())
                .flatMap(userEntity -> Mono.error(new UserExistException(userEntity.getUsername())))
                .switchIfEmpty(userRepository.save(user))
                .cast(UserEntity.class)
                .flatMap(userEntity ->
                        roleService.addRoleToUser(userEntity.getId(), defaultRole)
                                .then(Mono.just(userEntity))
                )
                .doOnNext(userEntity -> userEntity.setIsBlocked(false))
                .map(mapper::db2UserDto)
                .doOnNext(userDto -> userDto.setRoles(List.of(defaultRole)));
    }

    public Mono<PageDto> getPage(String page, String size, String username) {

        Criteria criteria = Criteria.empty();

        if (!username.isBlank())
            criteria = criteria.and(Criteria.where("username").like(String.format("%%%s%%", username)).ignoreCase(true));

        Query query = Query.query(criteria)
                .sort(Sort.by("username"))
                .limit(Integer.parseInt(size))
                .offset((Long.parseLong(page) - 1) * Long.parseLong(size));

        return databaseClient.select(UserEntity.class).from("usr")
                .matching(query)
                .all()
                .map(mapper::db2UserDto)
                .concatMap(userDto -> roleService.findByUserId(userDto.getId())
                        .flatMap(roles -> {
                            userDto.setRoles(roles);
                            return Mono.just(userDto);
                        }))
                .collectList()
                .zipWith(
                        databaseClient.select(UserEntity.class)
                                .from("usr")
                                .matching(query).count())
                .map(t -> new PageDto<>(t.getT1(), t.getT2(), Integer.parseInt(page) - 1, Integer.parseInt(size)));
    }
}
