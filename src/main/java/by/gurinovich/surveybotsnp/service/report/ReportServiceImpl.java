package by.gurinovich.surveybotsnp.service.report;

import by.gurinovich.surveybotsnp.exception.SurveyBotDomainLogicException;
import by.gurinovich.surveybotsnp.service.survey.SurveyService;
import by.gurinovich.surveybotsnp.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final SurveyService surveyService;

    @Override
    public Resource generate(final Long chatId) {
        var completedSurveys = surveyService.findAllCompleted(chatId);

        try (var document = new XWPFDocument()) {
            XWPFTable table = document.createTable();
            XWPFTableRow headerRow = table.getRow(0);

            headerRow.getCell(0).setText("Имя");
            headerRow.addNewTableCell().setText("Email");
            headerRow.addNewTableCell().setText("Оценка");

            completedSurveys.forEach(survey -> {
                        XWPFTableRow row = table.createRow();
                        row.getCell(0).setText(survey.getName() != null ? survey.getName() : "-");
                        row.getCell(1).setText(survey.getEmail() != null ? survey.getEmail() : "-");
                        row.getCell(2).setText(survey.getScore() != null ? survey.getScore().toString() : "-");
                    }
            );

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);

            return new ByteArrayResource(outputStream.toByteArray());
        } catch (IOException e) {
            log.error("Error generating report for chatId - '{}'", chatId);

            throw SurveyBotDomainLogicException.internalServerError(MessageConstants.REPORT_GENERATING_ERROR, chatId);
        }
    }
}
