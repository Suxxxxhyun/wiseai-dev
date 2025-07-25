package com.wiseai;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptTest {

	@Test
	public void jasyptTest(){
		String password = "sakncksjallkasdkl#$@^#*asdsiajodias2737";
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		encryptor.setPoolSize(4);
		encryptor.setPassword(password);
		encryptor.setAlgorithm("PBEWithMD5AndTripleDES"); // 암호화 알고리즘 설정
		String content = "1234"; // 암호화할 내용
		String encryptedContent = encryptor.encrypt(content); // 암호화
		String decryptedContent = encryptor.decrypt(encryptedContent);
		System.out.println("Enc : " + encryptedContent + ", Dec : " + decryptedContent);
	}
}
