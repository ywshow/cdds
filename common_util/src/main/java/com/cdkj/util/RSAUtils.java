package com.cdkj.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.util.Assert;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Utils - RSA加密/解密
 *
 * @author SHOP++ Team
 * @version 4.0
 */
public final class RSAUtils {
    public static PrivateKey privateKey = null;
    public static PublicKey publicKey = null;

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 加密/解密算法
     */
    private static final String TRANSFORMATION = "RSA"; //java//"RSA/ECB/PKCS1Padding"; 安卓：//"RSA/ECB/NoPadding
//    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding"; //安卓：//"RSA/ECB/NoPadding"
    /**
     * 安全服务提供者
     */
    private static final Provider PROVIDER = new BouncyCastleProvider();

    /**
     * 不可实例化
     */
    private RSAUtils() {
    }

    /**
     * 生成密钥对
     *
     * @param keySize 密钥大小
     * @return 密钥对
     */
    public static KeyPair generateKeyPair(int keySize) {
        Assert.state(keySize > 0);

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM, PROVIDER);
            keyPairGenerator.initialize(keySize);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 生成私钥
     *
     * @param encodedKey 密钥编码
     * @return 私钥
     */
    public static PrivateKey generatePrivateKey(byte[] encodedKey) {
        Assert.notNull(encodedKey);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 生成私钥
     *
     * @param keyString 密钥字符串(BASE64编码)
     * @return 私钥
     */
    public static PrivateKey generatePrivateKey(String keyString) {
        Assert.hasText(keyString);

        return generatePrivateKey(Base64.decodeBase64(keyString));
    }

    /**
     * 生成公钥
     *
     * @param encodedKey 密钥编码
     * @return 公钥
     */
    public static PublicKey generatePublicKey(byte[] encodedKey) {
        Assert.notNull(encodedKey);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);
            return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 生成公钥
     *
     * @param keyString 密钥字符串(BASE64编码)
     * @return 公钥
     */
    public static PublicKey generatePublicKey(String keyString) {
        Assert.hasText(keyString);

        return generatePublicKey(Base64.decodeBase64(keyString));
    }

    /**
     * 获取密钥字符串
     *
     * @param key 密钥
     * @return 密钥字符串(BASE64编码)
     */
    public static String getKeyString(Key key) {
        Assert.notNull(key);

        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 获取密钥
     *
     * @param type        类型
     * @param inputStream 输入流
     * @param password    密码
     * @return 密钥
     */
    public static Key getKey(String type, InputStream inputStream, String password) {
        Assert.hasText(type);
        Assert.notNull(inputStream);

        try {
            KeyStore keyStore = KeyStore.getInstance(type, PROVIDER);
            keyStore.load(inputStream, password != null ? password.toCharArray() : null);
            String alias = keyStore.aliases().hasMoreElements() ? keyStore.aliases().nextElement() : null;
            return keyStore.getKey(alias, password != null ? password.toCharArray() : null);
        } catch (KeyStoreException | NoSuchAlgorithmException | IOException | CertificateException | UnrecoverableKeyException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 获取证书
     *
     * @param type        类型
     * @param inputStream 输入流
     * @return 证书
     */
    public static Certificate getCertificate(String type, InputStream inputStream) {
        Assert.hasText(type);
        Assert.notNull(inputStream);

        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(type, PROVIDER);
            return certificateFactory.generateCertificate(inputStream);
        } catch (CertificateException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 生成签名
     *
     * @param algorithm  签名算法
     * @param privateKey 私钥
     * @param data       数据
     * @return 签名
     */
    public static byte[] sign(String algorithm, PrivateKey privateKey, byte[] data) {
        Assert.hasText(algorithm);
        Assert.notNull(privateKey);
        Assert.notNull(data);

        try {
            Signature signature = Signature.getInstance(algorithm, PROVIDER);
            signature.initSign(privateKey);
            signature.update(data);
            return signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 验证签名
     *
     * @param algorithm 签名算法
     * @param publicKey 公钥
     * @param sign      签名
     * @param data      数据
     * @return 是否验证通过
     */
    public static boolean verify(String algorithm, PublicKey publicKey, byte[] sign, byte[] data) {
        Assert.hasText(algorithm);
        Assert.notNull(publicKey);
        Assert.notNull(sign);
        Assert.notNull(data);

        try {
            Signature signature = Signature.getInstance(algorithm, PROVIDER);
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(sign);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 验证签名
     *
     * @param algorithm   签名算法
     * @param certificate 证书
     * @param sign        签名
     * @param data        数据
     * @return 是否验证通过
     */
    public static boolean verify(String algorithm, Certificate certificate, byte[] sign, byte[] data) {
        Assert.hasText(algorithm);
        Assert.notNull(certificate);
        Assert.notNull(sign);
        Assert.notNull(data);

        try {
            Signature signature = Signature.getInstance(algorithm, PROVIDER);
            signature.initVerify(certificate);
            signature.update(data);
            return signature.verify(sign);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 加密
     *
     * @param publicKey 公钥
     * @param data      数据
     * @return 密文
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] data) {
        Assert.notNull(publicKey);
        Assert.notNull(data);

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 解密
     *
     * @param privateKey 私钥
     * @param data       数据
     * @return 明文
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
        Assert.notNull(privateKey);
        Assert.notNull(data);

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 保存密钥到本地
     *
     * @param keyPair
     * @param publicKeyFile
     * @param privateKeyFile
     * @throws ConfigurationException
     */
    public static void saveKey(KeyPair keyPair, String publicKeyFile,
                               String privateKeyFile) throws ConfigurationException {
        PublicKey pubkey = keyPair.getPublic();
        PrivateKey prikey = keyPair.getPrivate();
        // save public key
        PropertiesConfiguration publicConfig = new PropertiesConfiguration(
                publicKeyFile);
        publicConfig.setProperty("PU", Hex.toHexString(pubkey.getEncoded()));
        publicConfig.save();

        // save private key
        PropertiesConfiguration privateConfig = new PropertiesConfiguration(
                privateKeyFile);
        privateConfig.setProperty("PR", Hex.toHexString(prikey.getEncoded()));
        privateConfig.save();
    }

    /**
     * @param filename
     * @param type：    1-public 0-private
     * @return
     * @throws ConfigurationException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static Key loadKey(String filename, int type)
            throws ConfigurationException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        PropertiesConfiguration config = new PropertiesConfiguration(filename);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, new BouncyCastleProvider());

        if (type == 0) {
            // privateKey
            String privateKeyValue = config.getString("PR");
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Hex.decode(privateKeyValue));
            PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);
            return privateKey;

        } else {
            // publicKey
            String privateKeyValue = config.getString("PU");
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Hex.decode(privateKeyValue));
            PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
            return publicKey;
        }
    }

    /**
     * @param keyStr
     * @param type：  1-public 0-private
     * @return
     * @throws ConfigurationException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static void setKey(String keyStr, int type) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, new BouncyCastleProvider());

        if (type == 0) {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Hex.decode(keyStr));
            privateKey = keyFactory.generatePrivate(priPKCS8);
//            System.out.println("privateKey=" + privateKey);
        } else if (type == 1) {
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Hex.decode(keyStr));
            publicKey = keyFactory.generatePublic(bobPubKeySpec);
//            System.out.println("publicKey=" + publicKey);
        }
    }

    /**
     * @param keySpec
     * @param type：   1-public 0-private
     * @return
     * @throws ConfigurationException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static Key getKeyBySpec(String keySpec, int type)
            throws ConfigurationException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, new BouncyCastleProvider());
        if (type == 0) {
            // privateKey
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Hex.decode(keySpec));
            PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);
            return privateKey;
        } else {
            // publicKey
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Hex.decode(keySpec));
            PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
            return publicKey;
        }
    }

    /**
     * 使用默认的私钥解密给定的字符串。
     * <p/>
     * 若{@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。
     * 私钥不匹配时，返回 {@code null}。
     *
     * @param encrypttext 密文。
     * @return 原文字符串。
     */
    public static String decryptString(PrivateKey key, String encrypttext) {
        if (StringUtil.isEmpty(encrypttext)) {
            return null;
        }
        try {
            byte[] en_data = Hex.decode(encrypttext);
            byte[] data = decrypt(key, en_data);
            return new String(data);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 使用默认的私钥解密由JS加密（使用此类提供的公钥加密）的字符串。
     *
     * @param encrypttext 密文。
     * @return {@code encrypttext} 的原文字符串。
     */
    public static String decryptStringByJs(PrivateKey key, String encrypttext) {
        String text = decryptString(key, encrypttext);
        if (text == null) {
            return null;
        }
        return StringUtils.reverse(text);
    }

    /**
     * 使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成密钥对，保存到本地
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            RSAPublicKey publicKey = (RSAPublicKey) RSAUtils.loadKey("F:\\work\\express\\pu.pu", 1);
            RSAPrivateKey privateKey = (RSAPrivateKey) RSAUtils.loadKey("F:\\work\\express\\pr.pr", 0);
            System.out.println(publicKey);
            System.out.println(privateKey);

            //公钥-系数(n)
            System.out.println("public key modulus:" + publicKey.getModulus());

            //公钥-指数(e1)
            System.out.println("public key exponent:" + publicKey.getPublicExponent());
            System.out.println("-------------------------------");
        } catch (ConfigurationException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}