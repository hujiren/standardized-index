package com.sutpc.its.config;
/*
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestClientConfig {

  //private Logger logger =LogManager.getLogger(RestClientConfig.class);

  /**
   * .
   */
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    //restTemplate.setRequestFactory(clientHttpRequestFactory());
    restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
    return restTemplate;
  }

  /*@Bean
  public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
    try {
      HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
      SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
          return true;
        }
      }).build();
      httpClientBuilder.setSSLContext(sslContext);
      HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
      SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
          sslContext, hostnameVerifier);
      Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
          .<ConnectionSocketFactory>create()
          .register("http", PlainConnectionSocketFactory.getSocketFactory())
          .register("https", sslConnectionSocketFactory).build();// ??????http???https??????
      // ?????????????????????
      PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
        new PoolingHttpClientConnectionManager(
          socketFactoryRegistry);
      poolingHttpClientConnectionManager.setMaxTotal(500); // ???????????????500
      poolingHttpClientConnectionManager.setDefaultMaxPerRoute(100); // ??????????????????100
      httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
      httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true)); // ????????????
      HttpClient httpClient = httpClientBuilder.build();
      HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
        new HttpComponentsClientHttpRequestFactory(
          httpClient); // httpClient????????????
      clientHttpRequestFactory.setConnectTimeout(20000);              // ????????????
      clientHttpRequestFactory.setReadTimeout(30000);                 // ????????????????????????
      clientHttpRequestFactory.setConnectionRequestTimeout(20000);    // ??????????????????????????????
      return clientHttpRequestFactory;
    } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
      logger.error("?????????HTTP???????????????", e);
    }
    return null;
  }*/
}
