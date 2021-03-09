package main.repositories;

import main.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {
    List<Users> findByIsModeratorTrue();
    List<Users> findByIsModeratorFalse();
    Users findByEmail(String email);
    Users findByCode(String code);
}