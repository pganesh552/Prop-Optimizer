package com.personal.repository;

import com.personal.model.PlayerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerInfoRepository extends JpaRepository<PlayerInfo, Long> {

    Optional<PlayerInfo> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT p.name FROM PlayerInfo p")
    List<String> findAllPlayerNames();

    List<PlayerInfo> findByTeam(String team);
}