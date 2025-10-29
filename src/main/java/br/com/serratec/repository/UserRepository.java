package br.com.serratec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);
}

