package learn.example.hibernateeventlisteners.data.repositories;

import learn.example.hibernateeventlisteners.data.models.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findTop1ByUserName(String userName);

}
