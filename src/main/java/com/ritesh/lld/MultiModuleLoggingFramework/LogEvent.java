package com.ritesh.lld.MultiModuleLoggingFramework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogEvent {
    int id;
    String level;
    String message;
    String moduleName;
}
