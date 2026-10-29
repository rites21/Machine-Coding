package com.ritesh.lld.MultiModuleLoggingFramework;

public class FileLogSink implements LogSink{

    @Override
    public void write(LogEvent event) {
        //write
        System.out.println(event);
    }
}
