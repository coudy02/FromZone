//package com.yizhen.testokhttp;
//
//
//import android.content.Context;
//import android.util.Log;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.HurlStack;
//import com.android.volley.toolbox.Volley;
//import com.zjbl.business.BuildConfig;
//import com.zjbl.business.utils.StringRequestWrapper;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.KeyStore;
//import java.security.SecureRandom;
//import java.security.cert.CertificateFactory;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManagerFactory;
//
///**
// * Created by Administrator on 2015/10/22.
// */
//public class RequestController {
//
//    private static RequestController instance;
//    private RequestQueue mQueue;
//
//    private static final String DEBUG_CERTIFICATE = "取得证书的内容";
//    private static final String RELEASE_CERTIFICATE = "取得证书的内容";
//    private RequestController(Context context) {
//        InputStream inputStream = null;
//        try {
//            if (!BuildConfig.DEBUG){
//                inputStream = new ByteArrayInputStream(RELEASE_CERTIFICATE.getBytes("UTF-8"));
//            } else {
//                inputStream = new ByteArrayInputStream(DEBUG_CERTIFICATE.getBytes("UTF-8"));
//            }
//        } catch (Exception e){
//            Log.e("RequestController", "e = "+e.getMessage()+"-----获取证书信息错误");
//        }
//        mQueue = Volley.newRequestQueue(context, new HurlStack(null, createCertificates(inputStream)));
////        mQueue = Volley.newRequestQueue(context);
//    }
//
//    public static RequestController getRequestControllerInstance(Context context){
//        if (instance == null){
//            synchronized (RequestController.class){
//                if (instance == null){
//                    instance = new RequestController(context);
//                }
//            }
//        }
//        return instance;
//    }
//    public RequestQueue getQueue(){
//        return mQueue;
//    }
//    public void request(StringRequestWrapper stringRequestWrapper){
//        mQueue.add(stringRequestWrapper.stringRequest);
//    }
//
//    /**    构造CertificateFactory对象，通过它的generateCertificate(is)方法得到Certificate。
//     //    然后讲得到的Certificate放入到keyStore中。
//     //    接下来利用keyStore去初始化我们的TrustManagerFactory
//     //    由trustManagerFactory.getTrustManagers获得TrustManager[]初始化我们的SSLContext
//     //    最后，sslSocketFactory即可
//     */
//    public SSLSocketFactory createCertificates(InputStream... certificates) {
//        SSLSocketFactory sslSocketFactory = null;
//        try {
//            //CertificateFactory此类定义了用于从相关的编码中生成证书、证书路径 (CertPath) 和证书撤消列表 (CRL) 对象的 CertificateFactory 功能
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keyStore.load(null);
//            int index = 0;
//            for (InputStream certificate : certificates) {
//                String certificateAlias = Integer.toString(index++);
//                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
//
//                try {
//                    if (certificate != null)
//                        certificate.close();
//                } catch (IOException e) {
//                }
//            }
//
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//
//            TrustManagerFactory trustManagerFactory =
//                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//
//            trustManagerFactory.init(keyStore);
//            sslContext.init
//                    (
//                            null,
//                            trustManagerFactory.getTrustManagers(),
//                            new SecureRandom()
//                    );
//            Log.e("RequestController", "sslContext = " + sslContext.getProtocol() + "---" + sslContext.getServerSocketFactory() + "---" + sslContext.getSocketFactory()
//                    + "----" + sslContext.getDefaultSSLParameters());
//            sslSocketFactory = sslContext.getSocketFactory();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            return sslSocketFactory;
//        }
//
//    }
//}