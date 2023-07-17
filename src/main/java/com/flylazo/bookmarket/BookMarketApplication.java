package com.flylazo.bookmarket;

import jakarta.servlet.ServletContext;
import jakarta.servlet.SessionTrackingMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import java.util.EnumSet;

@SpringBootApplication
public class BookMarketApplication implements ServletContextInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BookMarketApplication.class, args);
		
	}

	@Override
	public void onStartup(ServletContext servletContext) {
		servletContext.setSessionTimeout(60);
		servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		var sessionCookieConfig = servletContext.getSessionCookieConfig();
		sessionCookieConfig.setHttpOnly(true);
	}
}
