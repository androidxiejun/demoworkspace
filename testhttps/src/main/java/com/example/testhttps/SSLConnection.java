package com.example.testhttps;

/**
 * Created by AndroidXJ on 2018/12/26.
 */

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;

/**
 * Created by Administrator on 2016/7/11.
 */
public class SSLConnection {
    private static TrustManager[] trustManagers;
    private static final String KEY_STORE_TYPE_BKS = "bks";
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";
    private static final String keyStoreFileName = Environment.getExternalStorageDirectory().getPath() + "/asset/client.p12";
    private static final String keyStorePassword = "123456";
    private static final String trustStoreFileName = Environment.getExternalStorageDirectory().getPath() + "/asset/server.bks";
    private static final String trustStorePassword = "123456";
    private static final String alias = null;//"client";
    private static Context pContext = null;

    public static final String PUB_KEY="MIIDUzCCAjugAwIBAgIEW8IpDDANBgkqhkiG9w0BAQsFADBaMQswCQYDVQQGEwJj\n" +
            "bjELMAkGA1UECBMCemoxCzAJBgNVBAcTAmh6MQ4wDAYDVQQKEwVhcHBsZTEOMAwG\n" +
            "A1UECxMFYXBwbGUxETAPBgNVBAMTCHhqdW4uY29tMB4XDTE4MTIyNzA4MzgzNloX\n" +
            "DTE5MTIyNzA4MzgzNlowWjELMAkGA1UEBhMCY24xCzAJBgNVBAgTAnpqMQswCQYD\n" +
            "VQQHEwJoejEOMAwGA1UEChMFYXBwbGUxDjAMBgNVBAsTBWFwcGxlMREwDwYDVQQD\n" +
            "Ewh4anVuLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKWQfc5R\n" +
            "2dwS638SIER/7eaNTQvsyodVXXrvSwOq6cJSsVrc0Spki3pmvqSeDGhRJLc+3rzX\n" +
            "MypOEYh1Dxn5YZsIWR/HBGUeNfYfOxPiA9lqbgAqrmAHPuycRXDMOXF0lcX/fzXp\n" +
            "eVERMfNMSYiFsJavH1CnvTvIGqj+YHPox4GHD9X5D2ZfhmwDrtLsdpoZnw5IzST9\n" +
            "8oFOcSfMCY2i0Yni13cGo+eRKzYqOF7q7o4fiiGohvHxe7dzjh+VXztRv4CDMyzd\n" +
            "jgc4oh1OvuX1PDVWIMid7hv5YkhNn2qzpfFPbOjBBjvTE+YGzCmpimVFTbVEgQVk\n" +
            "VQG7hq9q2CWKVVUCAwEAAaMhMB8wHQYDVR0OBBYEFJ1SpNcCyc554qnS2Hjcy60+\n" +
            "4gQvMA0GCSqGSIb3DQEBCwUAA4IBAQAzZl0jcsjTZqj5rcfgqEcl30uRHd5eCtMR\n" +
            "HzE2HE4TmpP2UT2g+IkDfRIWbdrvdywgPdGAaW9nNnZd+Lk72wuRJlAhuH2aYRMT\n" +
            "WkBwq/LZRTA3fxh0P3WNNu83fgRr6L0h60hbaQAnS9frYLQ5jzUstwqCoXNJ+qEL\n" +
            "OrQ9dTw7/aZT/R0BVP/iaW8RYhv5lV3JA7RXG0ITH28B4UQmEaLP0ij9gzH4lT6z\n" +
            "cze+bqTpgxJM7wFFxFnwGNqbNW1QFQWLXSIQquEYA23cC7ppC+dL5Wqu9l3Z5axI\n" +
            "XF+F/ujR3GFGWkpO/E7FbfjnATzi1W67Wg1spKj9H74EX+waxccY";

    public static void allowAllSSL() {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        SSLContext context;
        if (trustManagers == null) {
            try {
                KeyManager[] keyManagers = createKeyManagers(keyStoreFileName, keyStorePassword, alias);
                trustManagers = createTrustManagers(trustStoreFileName, trustStorePassword);
                context = SSLContext.getInstance("TLS");
                context.init(keyManagers, trustManagers, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                Log.e("allowAllSSL", e.toString());
            }//new TrustManager[]{new _FakeX509TrustManager()};
        }
    }

    private static KeyManager[] createKeyManagers(String keyStoreFileName, String eyStorePassword, String alias)
            throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        FileInputStream inputStream = new FileInputStream(keyStoreFileName);
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
        keyStore.load(inputStream, keyStorePassword.toCharArray());
        printKeystoreInfo(keyStore);//for debug
        KeyManager[] managers;
        if (alias != null) {
            managers =
                    new KeyManager[]{
                            new SSLConnection().new AliasKeyManager(keyStore, alias, keyStorePassword)};
        } else {
            KeyManagerFactory keyManagerFactory =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());//PKIX "X509")
            keyManagerFactory.init(keyStore, keyStorePassword == null ? null : keyStorePassword.toCharArray());
            managers = keyManagerFactory.getKeyManagers();
        }
        return managers;
    }

    private static TrustManager[] createTrustManagers(String trustStoreFileName, String trustStorePassword)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        FileInputStream inputStream = new FileInputStream(trustStoreFileName);
        KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);
        trustStore.load(inputStream, trustStorePassword.toCharArray());
        printKeystoreInfo(trustStore);//for debug
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        return trustManagerFactory.getTrustManagers();
    }

    private static void printKeystoreInfo(KeyStore keystore) throws KeyStoreException {
        System.out.println("Provider : " + keystore.getProvider().getName());
        System.out.println("Type : " + keystore.getType());
        System.out.println("Size : " + keystore.size());
        Enumeration en = keystore.aliases();
        while (en.hasMoreElements()) {
            System.out.println("Alias: " + en.nextElement());
        }
    }

    private class AliasKeyManager implements X509KeyManager {
        private KeyStore _ks;
        private String _alias;
        private String _password;

        public AliasKeyManager(KeyStore ks, String alias, String password) {
            _ks = ks;
            _alias = alias;
            _password = password;
        }

        public String chooseClientAlias(String[] str, Principal[] principal, Socket socket) {
            return _alias;
        }

        public String chooseServerAlias(String str, Principal[] principal, Socket socket) {
            return _alias;
        }

        public X509Certificate[] getCertificateChain(String alias) {
            try {
                Certificate[] certificates = this._ks.getCertificateChain(alias);
                if (certificates == null) {
                    throw new FileNotFoundException("no certificate found for alias:" + alias);
                }
                X509Certificate[] x509Certificates = new X509Certificate[certificates.length];
                System.arraycopy(certificates, 0, x509Certificates, 0, certificates.length);
                return x509Certificates;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public String[] getClientAliases(String str, Principal[] principal) {
            return new String[]{_alias};
        }

        public PrivateKey getPrivateKey(String alias) {
            try {
                return (PrivateKey) _ks.getKey(alias, _password == null ? null : _password.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public String[] getServerAliases(String str, Principal[] principal) {
            return new String[]{_alias};
        }
    }
}
