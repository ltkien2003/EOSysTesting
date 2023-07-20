package com.eos.testcase;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Argon2ToString {
	public static void main(String[] args) {
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		System.out.println(Argon2ToString.argon2ToString("Khang123@456"));
//		System.out.println(argon2.verify(Argon2ToString.argon2ToString("Khang@123456"), "Khang@123456"));
	}
	public static String argon2ToString(String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashedPassword = argon2.hash(10, 65536, 1, password);
        return hashedPassword;
	}
}
