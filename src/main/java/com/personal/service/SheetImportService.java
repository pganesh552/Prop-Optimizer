package com.personal.service;

import com.personal.model.PlayerInfo;
import com.personal.repository.PlayerInfoRepository;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SheetImportService {

    private final PlayerInfoRepository playerInfoRepository;
    private final String sheetId;
    private final String sheetName;
    private final int headerRow;
    private final String credentialsPath;

    private final Logger logger = LoggerFactory.getLogger(SheetImportService.class);

    public SheetImportService(PlayerInfoRepository playerInfoRepository,
                              @Value("${excel.import.sheet-id}") String sheetId,
                              @Value("${excel.import.sheet-name:Sheet1}") String sheetName,
                              @Value("${excel.import.header-row:0}") int headerRow,
                              @Value("${google.credentials.file}") String credentialsPath) {
        this.playerInfoRepository = playerInfoRepository;
        this.sheetId = sheetId;
        this.sheetName = sheetName;
        this.headerRow = headerRow;
        this.credentialsPath = credentialsPath;
    }

    @Transactional
    public void importFromGoogleSheets() throws Exception {
        List<List<Object>> rows = loadFromGoogleSheet();

        if (rows == null || rows.isEmpty()) {
            logger.warn("⚠️ No data found in the sheet.");
            return;
        }

        List<PlayerInfo> toSave = new ArrayList<>();
        for (int i = headerRow + 1; i < rows.size(); i++) {
            List<Object> cells = rows.get(i);
            if (cells.isEmpty()) continue;

            Long id = safeParseLong(getCellValue(cells, 0));
            if (id == null) continue;

            String name = safeParseString(getCellValue(cells, 1));
            String team = safeParseString(getCellValue(cells, 2));
            Integer points = safeParseInt(getCellValue(cells, 3));
            Integer rebounds = safeParseInt(getCellValue(cells, 4));
            Integer assists = safeParseInt(getCellValue(cells, 5));
            Integer minutes = safeParseInt(getCellValue(cells, 6));
            Integer offensivePossessions = safeParseInt(getCellValue(cells, 7));

            Optional<PlayerInfo> existing = playerInfoRepository.findById(id);
            PlayerInfo player = existing.orElseGet(PlayerInfo::new);

            player.setId(id);
            player.setName(name);
            player.setTeam(team);
            player.setPoints(points);
            player.setRebounds(rebounds);
            player.setAssists(assists);
            player.setMinutes(minutes);
            player.setOffensivePossessions(offensivePossessions);

            toSave.add(player);
        }

        if (!toSave.isEmpty()) {
            playerInfoRepository.saveAll(toSave);
            logger.info("✅ Imported {} player records from Google Sheets.", toSave.size());
        }
    }

    private List<List<Object>> loadFromGoogleSheet() throws IOException {
        Credential credential = GoogleCredential.fromStream(
                new ClassPathResource(credentialsPath).getInputStream()
        ).createScoped(Collections.singletonList("https://www.googleapis.com/auth/spreadsheets.readonly"));

        Sheets sheetsService = new Sheets.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                credential
        ).setApplicationName("Player Info Importer")
                .build();

        ValueRange response = sheetsService.spreadsheets().values()
                .get(sheetId, sheetName)
                .execute();

        return response.getValues();
    }

    private String getCellValue(List<Object> row, int index) {
        return (index < row.size() && row.get(index) != null) ? row.get(index).toString() : "";
    }

    private String safeParseString(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }

    private Integer safeParseInt(String value) {
        try {
            return (value == null || value.isEmpty()) ? null : Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            logger.error("Invalid integer value: {}", value);
            return null;
        }
    }

    private Long safeParseLong(String value) {
        try {
            return (value == null || value.isEmpty()) ? null : Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            logger.error("Invalid long value: {}", value);
            return null;
        }
    }
}
