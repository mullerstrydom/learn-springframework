package learn.example.hibernateeventlisteners.data.services;

import learn.example.hibernateeventlisteners.data.models.UserEntity;
import learn.example.hibernateeventlisteners.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void create(UserEntity user) {
        userRepository.save(user);
    }

    public UserEntity findByUserName(String userName) {
        return userRepository.findTop1ByUserName(userName).orElse(null);
    }
    public List<UserEntity> findAll() {
        List<UserEntity> userEntityList = new ArrayList<>();
        userRepository.findAll().forEach(userEntityList::add);
        return userEntityList;
    }
}
