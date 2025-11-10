package com.personal.service;

import com.personal.model.PlayerInfo;
import com.personal.repository.PlayerInfoRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelService {

    private final PlayerInfoRepository playerInfoRepository;
    private final String filePath;
    private final String sheetName;
    private final int headerRow;

    private final Logger logger = LoggerFactory.getLogger(ExcelService.class);

    public ExcelService(PlayerInfoRepository playerInfoRepository,
                        @Value("${excel.import.file-path}") String filePath,
                        @Value("${excel.import.sheet-name:Sheet1}") String sheetName,
                        @Value("${excel.import.header-row:0}") int headerRow) {
        this.playerInfoRepository = playerInfoRepository;
        this.filePath = filePath;
        this.sheetName = sheetName;
        this.headerRow = headerRow;
    }

    @Transactional
    public void importFromExcel() throws Exception {
        try (InputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet;
            if (sheetName == null || sheetName.isEmpty()) {
                throw new IllegalArgumentException("Sheet name is null or empty");
            } else {
                sheet = workbook.getSheet(sheetName);
            }

            Iterator<Row> rowIterator = sheet.iterator();
            int currentRowId = 0;
            while (rowIterator.hasNext() && currentRowId ++ <= headerRow) {
                rowIterator.next();
            }

            List<PlayerInfo> toSave = new ArrayList<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (isRowEmpty(row)) continue;

                Long id = safeGetLong(row.getCell(0));
                if (id == null) {
                    continue;
                }

                String name = safeGetString(row.getCell(1));
                String team = safeGetString(row.getCell(2));
                Integer points = safeGetInt(row.getCell(3));
                Integer rebounds = safeGetInt(row.getCell(4));
                Integer assists = safeGetInt(row.getCell(5));
                Integer minutes = safeGetInt(row.getCell(6));
                Integer offensivePossessions = safeGetInt(row.getCell(7));

                Optional<PlayerInfo> existingPlayer = playerInfoRepository.findById(id);
                PlayerInfo player = existingPlayer.orElseGet(PlayerInfo::new);
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
            }
        }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int c = 0; c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) return false;
        }
        return true;
    }

    private String safeGetString(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue().trim();
        if (cell.getCellType() == CellType.NUMERIC) return String.valueOf((long)cell.getNumericCellValue());
        if (cell.getCellType() == CellType.BOOLEAN) return String.valueOf(cell.getBooleanCellValue());
        return cell.toString().trim();
    }

    private Integer safeGetInt(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (int) cell.getNumericCellValue();
            }
            String s = safeGetString(cell);
            if (s == null || s.isEmpty()) return null;
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            logger.error("NumberFormatException", e);
        }

        return null;
    }

    private Long safeGetLong(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (long) cell.getNumericCellValue();
            }
            String s = safeGetString(cell);
            if (s == null || s.isEmpty()) return null;
            return Long.parseLong(s.trim());
        } catch (NumberFormatException e) {
            logger.error("NumberFormatException", e);
        }

        return null;
    }
}
