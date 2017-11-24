package de.fh_dortmund.randomerror.cw.chat.server.beans;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;

import de.fh_dortmund.randomerror.cw.chat.interfaces.HashingServiceLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.HashingServiceRemote;

@Stateless
public class HashingServiceBean implements HashingServiceLocal, HashingServiceRemote {

	 private MessageDigest encoder;
	 @Resource(name ="HashAlgorithm")
	 private  String HashAlgorithm;
	 
	@PostConstruct
	private void init() {
		try {
			encoder = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String generateHash(String plaintext) {
		String hash;
		hash = String.format("%040x", new BigInteger(1, encoder.digest(plaintext.getBytes())));
		return hash;
	}

}
