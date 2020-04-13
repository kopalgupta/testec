package com.testec.spring.boot.elasticcache.redis.service;

import com.testec.spring.boot.elasticcache.redis.service.dto.FxDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FxRateService {

    @Cacheable(value = "getWriteFxRateResult", key="{#currency}", cacheManager = "cacheManager1Hour")
    public Optional<FxDTO> getWriteFxRateResult(String currency, double rate) {
        try {
        	
            FxDTO fxDTO = new FxDTO();
            fxDTO.setFxRate(currency, rate);
            // Thread.sleep(2000); // 2 Second Delay to Simulate Workload
            return Optional.of(fxDTO);
//        } catch (InterruptedException e) {
//            return Optional.of(null);
//        }
        } catch (Exception e) {
        	System.out.print(e);
            return Optional.of(null);
        }
    }

    @CacheEvict(value = "resetFxRate", key = "#currency")
    public void resetFxRate(String currency) {
        // Intentionally blank
    }
    
    @Cacheable(value = "getFxRate", key="{#currency}")
    public Optional<Double> readFxRate(String currency) {
        try {
            FxDTO fxDTO = new FxDTO();
            double rate = fxDTO.getFxRate(currency);
            //Thread.sleep(2000); // 2 Second Delay to Simulate Workload
            return Optional.of(rate);
//        } catch (InterruptedException e) {
//            return Optional.of(null);
//        }
        } catch (Exception e) {
        	System.out.print(e);
            return Optional.of(null);
        }
    }
    
    @CachePut(value = "getUpdateFxRateResult", key="{#currency}", cacheManager = "cacheManager1Hour")
    public Optional<FxDTO> getUpdateFxRateResult(String currency, double rate) {
        try {
        	// resetFxRate(currency);
            FxDTO fxDTO = new FxDTO();
            fxDTO.updateFxRate(currency, rate);
            //Thread.sleep(2000); // 2 Second Delay to Simulate Workload
            return Optional.of(fxDTO);
//        } catch (InterruptedException e) {
//            return Optional.of(null);
//        }
        } catch (Exception e) {
            System.out.print(e);
        	return Optional.of(null);
        }
    }
    
}

