package de.htw.sdf.photoplatform.manager.impl;

import de.htw.sdf.photoplatform.manager.HashManager;
import de.htw.sdf.photoplatform.manager.common.DAOReferenceCollector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by patrick on 01.12.14.
 */
@Service
@Transactional
public class HashManagerImpl extends DAOReferenceCollector
        implements HashManager {

    private static final String SHA_512 = "SHA-512";

    @Override
    public String hash(String plain) throws NoSuchAlgorithmException {
        return this.hash(plain, SHA_512);
    }

    @Override
    public String hash(String plain, String algo)
            throws NoSuchAlgorithmException {
        byte[] hash = hashToByte(plain, algo);
        return this.byteHashToHexString(hash);
    }

    /**
     * @param value
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     */
    private byte[] hashToByte(String value, String algorithm)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        return md.digest(value.getBytes());
    }

    /**
     * @param hash
     * @return
     */
    private String byteHashToHexString(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash).toLowerCase();
    }
}
