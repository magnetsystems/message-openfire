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

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.sasl.SaslException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Implementation that uses the MMS server endpoint for token validation.
 */
public class BFOAuthTokenValidatorImpl implements BFOAuthTokenValidator {
  private static Logger LOGGER = LoggerFactory.getLogger(BFOAuthTokenValidatorImpl.class);

  private final static String DEFAULT_OAUTH_SERVER_ENDPOINT = "http://localhost:8443/api/tokens/token";
  private final static String METHOD = "GET";
  private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
  private final static int  HTTP_STATUS_OK = 200;

  public boolean isValid(String userId, String oauthToken) throws SaslException {
    HttpURLConnection connection = null;
    InputStream inputStream = null;
    boolean rv = false;
    try {
      connection = makeGetRequest(oauthToken);
      int responseCode = connection.getResponseCode();
      if (responseCode == HTTP_STATUS_OK) {
        inputStream = connection.getInputStream();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "utf-8"));
        TokenInfo tkInfo = gson.fromJson(reader, TokenInfo.class);
        LOGGER.info("TokenInfo : {}", tkInfo);
        if (tkInfo != null) {
          if (tkInfo.isAuthenticated()) {
            rv = true;
            List<String> roles = tkInfo.getRoles();
            String xid = userId;
            /*
             * update the roles for the user in user cache.
             */
            UserRoleCache.cacheRoles(xid, roles);
          } else {
            LOGGER.debug("Token:{} is unauthenticated. Token Info:{}", oauthToken, tkInfo);
          }
        }
      } else {
        String message = String.format("Unexpected Response code:%d from endpoint", responseCode);
        LOGGER.warn(message);
      }
      connection.disconnect();
      connection = null;
      return rv;
    } catch (IOException e) {
      LOGGER.warn("IO exception", e);
      throw new SaslException(e.getMessage());
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
    String oAuthServerEndPoint = JiveGlobals.getProperty("mmx.auth.endpoint.url", DEFAULT_OAUTH_SERVER_ENDPOINT);
    LOGGER.debug("Sending GET to " + oAuthServerEndPoint);
    HttpURLConnection conn = getConnection(oAuthServerEndPoint);
    conn.setDoOutput(true);
    conn.setUseCaches(false);
    conn.setRequestMethod(METHOD);
    conn.setRequestProperty("Content-Type", CONTENT_TYPE);
    conn.setRequestProperty("Authorization", "Bearer: " + token);
    return conn;
  }

  /**
   * Gets an {@link HttpURLConnection} given an URL.
   */
  protected static HttpURLConnection getConnection(String url) throws IOException {
    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
    return conn;
  }

  public static void main (String[] args ){
    String token = "A_pb_FSB6mi4V-K3kMcXEoLJCr_D0qem-sf_uOdSmCy_4ACyyv7vMJ5BbAwF8WQ9JGRG6wwPOXtgcUEqy-dviDtVNWpxy6RY-Tlr0FBMpl3GioXyBkRUM_nNhl5ynbr0UzbS3ppcwJo5bgaDzHKMjkg0bjsHVryE0Ef2cDHh7dNT8WSBKiTGVTabhvj3AqluV4MqQp4YQKu1TIws4rwWh9Wry6ERecvKPB9JB0f6weHMfcxg8hj2cBC9khCMN35SIdJ001EkTvE4DLIdFUImq52yDqjB7B8EvpfGhx0LoIQ";
    BFOAuthTokenValidator validator = new BFOAuthTokenValidatorImpl();
    try {
      boolean valid = validator.isValid("user", token);
      LOGGER.info("token:{} is {}", token, valid ? "valid" : "invalid");
    } catch (SaslException e) {
      LOGGER.warn("SaslException", e);
    }
  }
}
