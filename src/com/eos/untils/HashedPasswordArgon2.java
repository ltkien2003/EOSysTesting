package com.eos.untils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class HashedPasswordArgon2 {
	String stringToArgon2(String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashedPassword = argon2.hash(10, 65536, 1, password);
        return hashedPassword;
	}
	public static void main(String[] args) {
		HashedPasswordArgon2 h = new HashedPasswordArgon2();
		System.out.println(h.stringToArgon2("Khang@123456"));
	}
}
