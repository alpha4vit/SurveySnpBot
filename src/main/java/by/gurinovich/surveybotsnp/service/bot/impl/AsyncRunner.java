package by.gurinovich.surveybotsnp.service.bot.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class AsyncRunner {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void runAsync(Runnable task) {
        CompletableFuture.runAsync(() -> {
            try {
                task.run();
            } catch (Exception e) {
                log.error("Async task failed", e);
            }
        }, executor);
    }
}