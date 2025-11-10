package com.personal.controller;

import com.personal.model.PlayerInfo;
import com.personal.service.PlayerDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerDataController {

    private final PlayerDataService playerDataService;

    public PlayerDataController(PlayerDataService playerDataService) {
        this.playerDataService = playerDataService;
    }

    @PostMapping
    public ResponseEntity<PlayerInfo> saveOrUpdatePlayer(@RequestBody PlayerInfo playerInfo) {
        PlayerInfo saved = playerDataService.saveOrUpdatePlayerInfo(playerInfo);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<PlayerInfo>> getAllPlayers() {
        return ResponseEntity.ok(playerDataService.getAllPlayers());
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllPlayerNames() {
        return ResponseEntity.ok(playerDataService.getAllPlayerNames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerInfo> getPlayerById(@PathVariable Long id) {
        return playerDataService.getPlayerInfoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PlayerInfo> getPlayerByName(@PathVariable String name) {
        return playerDataService.getPlayerInfoByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/team/{team}")
    public ResponseEntity<List<PlayerInfo>> getPlayersByTeam(@PathVariable String team) {
        return ResponseEntity.ok(playerDataService.getPlayersByTeam(team));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerDataService.deletePlayerInfo(id);
        return ResponseEntity.noContent().build();
    }
}
