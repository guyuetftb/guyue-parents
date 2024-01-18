package com.gy.web.jetty.embedded;

import org.mockito.Mockito;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.UUID;

public class Test {

    public static void main(String[] args) {
        final String id = UUID.randomUUID().toString();
        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Mockito.when(request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE))
                .thenReturn("/file/" + id + "/output/genie/log.out");
        Mockito.when(request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE))
                .thenReturn("/file/{id}/**");

        final String encodedId = URLEncoder.encode(id, "UTF-8");
        final String expectedPath = "/api/v3/jobs/" + encodedId + "/output/genie/log.out";
    }
}


