package com.personal.service;

import com.personal.model.PlayerInfo;
import com.personal.repository.PlayerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerDataService {

    @Autowired
    private PlayerInfoRepository repository;

    public List<String> getAllPlayerNames() {
        return repository.findAllPlayerNames();
    }

    public List<PlayerInfo> getAllPlayers() {
        return repository.findAll();
    }

    public Optional<PlayerInfo> getPlayerInfoById(Long id) {
        return repository.findById(id);
    }

    public Optional<PlayerInfo> getPlayerInfoByName(String name) {
        return repository.findByName(name);
    }

    public List<PlayerInfo> getPlayersByTeam(String team) {
        return repository.findByTeam(team);
    }

    @Transactional
    public PlayerInfo savePlayerInfo(PlayerInfo playerInfo) {
        return repository.save(playerInfo);
    }

    @Transactional
    public PlayerInfo updatePlayerInfo(PlayerInfo playerInfo) {
        if (playerInfo.getId() == null || !repository.existsById(playerInfo.getId())) {
            throw new IllegalArgumentException("Player does not exist");
        }
        return repository.save(playerInfo);
    }

    @Transactional
    public PlayerInfo saveOrUpdatePlayerInfo(PlayerInfo playerInfo) {
        Optional<PlayerInfo> existing = repository.findByName(playerInfo.getName());

        existing.ifPresent(info -> playerInfo.setId(info.getId()));
        return repository.save(playerInfo);
    }

    public boolean playerExists(String name) {
        return repository.existsByName(name);
    }

    @Transactional
    public void deletePlayerInfo(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public List<PlayerInfo> saveAllPlayers(List<PlayerInfo> players) {
        return repository.saveAll(players);
    }
}