package com.ritesh.lld.Streams;

import java.util.Map;

record Student(
        String name,
        Map<String, Integer> marks
) {}
