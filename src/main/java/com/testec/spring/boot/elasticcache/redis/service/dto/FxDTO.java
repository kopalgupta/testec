package com.testec.spring.boot.elasticcache.redis.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FxDTO implements Serializable {
	
	public double fxrate;
	public String currency;
	
	public void setFxRate(String currency, double fxrate) {
		this.currency = currency;
		this.fxrate = fxrate;
	}
	
	public double getFxRate(String currency) {
		return this.fxrate;
	}
	
	public void updateFxRate(String currency, double fxrate) {
		this.currency = currency;
		this.fxrate = fxrate;
	}

}
