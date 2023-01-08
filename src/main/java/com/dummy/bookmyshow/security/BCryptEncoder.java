package com.dummy.bookmyshow.security;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptEncoder implements PasswordEncoder{

    private static final int STRENGTH = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String encode(CharSequence rawPassword) {
        return INSTANCE.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return INSTANCE.matches(rawPassword, encodedPassword);
    }

    /**
	 * Get the singleton {@link BCryptPasswordEncoder}.
	 */
	public static PasswordEncoder getInstance() {
		return INSTANCE;
	}

	private static final PasswordEncoder INSTANCE = new BCryptPasswordEncoder(STRENGTH, RANDOM);

	private BCryptEncoder() {
	}
    
}
