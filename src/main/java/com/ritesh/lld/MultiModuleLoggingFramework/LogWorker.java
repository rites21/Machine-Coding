package com.ritesh.lld.MultiModuleLoggingFramework;

import java.util.concurrent.BlockingQueue;

public class LogWorker implements Runnable {
    final LogSink logSink;
    BlockingQueue<LogEvent>  logQueue;

    public LogWorker(LogSink logSink, BlockingQueue<LogEvent> logQueue) {
        this.logSink = logSink;
        this.logQueue = logQueue;
    }

    @Override
    public void run() {
        consume();
    }

    private void consume() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                LogEvent event = logQueue.take();
                logSink.write(event);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
