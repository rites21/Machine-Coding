package com.ritesh.lld.MultiModuleLoggingFramework;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Logger {
   private final BlockingQueue<LogEvent> queue = new LinkedBlockingQueue<>(1000);


    public void log(LogEvent event){
        try {
           queue.put(event);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
