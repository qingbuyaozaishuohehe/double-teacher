package com.rzlearn.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName:HealthCheckController</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018-11-27 10:55
 **/
@RestController
@Slf4j
public class HealthCheckController {

    @RequestMapping(value = "/public/healthCheck", method = RequestMethod.GET)
    public int healthCheck() {
        return HttpStatus.SC_OK;
    }
}
