package uz.pdp.myFirstBot.controller.history;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import uz.pdp.myFirstBot.entity.HistoryEntity;
import uz.pdp.myFirstBot.repository.history.HistoryRepository;
import uz.pdp.myFirstBot.repository.history.HistoryRepositoryImpl;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HistoryController {

    HistoryRepository historyRepository = new HistoryRepositoryImpl();

    @SneakyThrows
    public SendDocument getAllHistoryInExcel(Long chatId){
        String[] row_heading = {"From", "To", "Entered amount", "Converted amount", "Date"};
        ArrayList<HistoryEntity> histories = historyRepository.getUsersHistory(chatId);

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet();
        Row headerRow = sheet.createRow(0);


        XSSFFont defaultFont= workbook.createFont();
        defaultFont.setFontHeightInPoints((short)10);
        defaultFont.setFontName("Arial");
        defaultFont.setColor(IndexedColors.BLACK.getIndex());
        defaultFont.setBold(false);
        defaultFont.setItalic(false);

        XSSFFont font= workbook.createFont();
        font.setFontHeightInPoints((short)10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.YELLOW.getIndex());
        font.setBold(true);
        font.setItalic(false);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBottomBorderColor(IndexedColors.GREEN.getIndex());
        style.setBorderBottom(BorderStyle.THICK);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 5000);

        for (int i = 0; i < row_heading.length; i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(row_heading[i]);
            cell.setCellStyle(style);
        }

        for (int i = 0; i < histories.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(histories.get(i).getEnteredType());
            dataRow.createCell(1).setCellValue(histories.get(i).getConvertedType());
            dataRow.createCell(2).setCellValue(histories.get(i).getNeedsToConvert());
            dataRow.createCell(3).setCellValue(histories.get(i).getConvertedAmount());
            dataRow.createCell(4).
                    setCellValue(histories.get(i)
                            .getConvertedDate()
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a")));
        }

        try (FileOutputStream outputStream = new FileOutputStream(new File("Histories.xls"))) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SendDocument sendDocument = new SendDocument(chatId.toString(), new InputFile(new File("/media/shakhzod/" +
                "2fed4cf4-6e79-4664-86d9-ee1b72f9388f/Intellij Ultimate Works/TelegramBotLesson/Histories.xls")));
        sendDocument.setCaption("This is your history");
        return sendDocument;
    }
}
