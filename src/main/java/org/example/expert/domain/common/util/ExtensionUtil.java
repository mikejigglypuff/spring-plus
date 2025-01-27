package org.example.expert.domain.common.util;

import java.util.HashMap;
import java.util.Map;

public class ExtensionUtil {
    private static final Map<String, String> EXTENSION_TO_MIME_TYPE_MAP = new HashMap<>();

    static {
        EXTENSION_TO_MIME_TYPE_MAP.put("jpg", "image/jpeg");
        EXTENSION_TO_MIME_TYPE_MAP.put("jpeg", "image/jpeg");
        EXTENSION_TO_MIME_TYPE_MAP.put("png", "image/png");
        // 필요한 확장자를 추가
    }

    public static String getMimeTypeForExtension(String extension) {
        return EXTENSION_TO_MIME_TYPE_MAP.getOrDefault(extension.toLowerCase(), "application/octet-stream");
    }
}
