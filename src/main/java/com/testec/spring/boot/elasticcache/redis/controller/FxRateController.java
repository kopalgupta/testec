package com.testec.spring.boot.elasticcache.redis.controller;

import com.testec.spring.boot.elasticcache.redis.controller.util.ResponseUtil;
import com.testec.spring.boot.elasticcache.redis.service.FxRateService;
import com.testec.spring.boot.elasticcache.redis.service.dto.FxDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.lang.String;

import java.util.Optional;

@RestController
@RequestMapping( value = "/fxrate" )
public class FxRateController {

    @Autowired
    private FxRateService fxRateService;

    @ApiOperation(
            value = "Write fx rate")
    @RequestMapping(value = "/write/{currency}/{rate}", method = RequestMethod.POST)
    public ResponseEntity<FxDTO> writefxrate(@PathVariable String currency, @PathVariable double rate) {
        Optional<FxDTO> res = fxRateService.getWriteFxRateResult(currency, rate);
        return ResponseUtil.wrapOrNotFound(res);
    }

    @ApiOperation(
            value = "Reset fx rate")
    @RequestMapping(value = "/reset/{currency}", method = RequestMethod.DELETE)
    public ResponseEntity<?> resetfxrate(@PathVariable String currency) {
    	fxRateService.resetFxRate(currency);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    @ApiOperation(
            value = "Read fx rate")
    @RequestMapping(value = "/{currency}", method = RequestMethod.GET)
    public ResponseEntity<Double> readfxrate(@PathVariable String currency) {
    	// double rate = fxRateService.readFxRate(currency);
    	Optional<Double> res = fxRateService.readFxRate(currency);
        return ResponseUtil.wrapOrNotFound(res);
    }
    
    @ApiOperation(
            value = "Update fx rate")
    @RequestMapping(value = "/update/{currency}/{rate}", method = RequestMethod.PUT)
    public ResponseEntity<FxDTO> updatefxrate(@PathVariable String currency, @PathVariable double rate) {
        Optional<FxDTO> res = fxRateService.getUpdateFxRateResult(currency, rate);
        return ResponseUtil.wrapOrNotFound(res);
    }


}

