package by.gurinovich.surveybotsnp.service.report;

import org.springframework.core.io.Resource;

public interface ReportService {

    Resource generate(Long chatId);

}
