/*   Copyright (c) 2015 Magnet Systems, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.magnet.mmx.sasl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * An accessor to the Blowfish OAuth mechanism.  It is the main integration
 * point to Blowfish.
 */
public class BFOAuthAccessor {
  private static Logger LOGGER = LoggerFactory.getLogger(BFOAuthAccessor.class);

  private final static String DEFAULT_OAUTH_SERVER_BASE_URL = "http://localhost:8443/api/com.magnet.server";
  private final static String AUTH_PATH = "/tokens/token";
  private final static String METHOD = "GET";
  private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
  private final static int  HTTP_STATUS_OK = 200;
  
  public static TokenInfo getTokenInfo(String oauthToken) throws IOException {
    TokenInfo tkInfo = null;
    HttpURLConnection connection = null;
    InputStream inputStream = null;
    try {
      connection = makeGetRequest(oauthToken);
      int responseCode = connection.getResponseCode();
      if (responseCode == HTTP_STATUS_OK) {
        inputStream = connection.getInputStream();
        Gson gson = new Gson();
        String responseBody = new java.util.Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();;
        LOGGER.debug("Token validation response : {}", responseBody);
        JsonReader reader = new JsonReader(new StringReader(responseBody));
        //JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "utf-8"));
        tkInfo = gson.fromJson(reader, TokenInfo.class);
      } else {
        String message = String.format("Unexpected Response code:%d from endpoint", responseCode);
        LOGGER.warn(message);
      }
      return tkInfo;
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
        }
      }
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  private static HttpURLConnection makeGetRequest(String token) throws IOException {
    String oAuthServerBaseUrl = JiveGlobals.getProperty("mmx.auth.server.base.url", DEFAULT_OAUTH_SERVER_BASE_URL);
    LOGGER.debug("Sending GET to " + oAuthServerBaseUrl+AUTH_PATH);
    HttpURLConnection conn = getConnection(oAuthServerBaseUrl+AUTH_PATH);
    conn.setDoOutput(true);
    conn.setUseCaches(false);
    conn.setRequestMethod(METHOD);
    conn.setRequestProperty("Content-Type", CONTENT_TYPE);
    conn.setRequestProperty("Authorization", "Bearer " + token);
    return conn;
  }

  /**
   * Gets an {@link HttpURLConnection} given an URL.
   */
  private static HttpURLConnection getConnection(String url) throws IOException {
    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
    return conn;
  }
}
