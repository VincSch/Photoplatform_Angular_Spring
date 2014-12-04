package de.htw.sdf.photoplatform.manager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

/**
 * Created by patrick on 01.12.14.
 */
public interface HashManager {

    /**
     * It hashs an String value. By default it uses
     * the SHA-512 algorithm.
     *
     * @param plain value that has to be hashed
     * @return
     */
    public String hash(String plain) throws NoSuchAlgorithmException;

    /**
     * It hashs an String value.
     *
     * @param plain value that has to be hashed
     * @param algo  Algorithm name as String value. e.g. ('MD%')
     * @return
     */
    public String hash(String plain, String algo)
        throws NoSuchAlgorithmException;
}
