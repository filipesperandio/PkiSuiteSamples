package com.lacunasoftware.suite.sample.config;

import com.lacunasoftware.suite.sample.util.Util;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("web-pki")
public class WebPkiProperties {

	private String license = null;

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		if (!Util.isNullOrEmpty(license)) {
			this.license = license;
		}
	}
}
