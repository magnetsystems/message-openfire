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

import java.util.List;

import javax.security.sasl.SaslException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation that uses the MMS server endpoint for token validation.
 */
public class BFOAuthTokenValidatorImpl implements BFOAuthTokenValidator {
  private static Logger LOGGER = LoggerFactory.getLogger(BFOAuthTokenValidatorImpl.class);

  /**
   * @param userName In the form of "userID%appID".
   * @param oauthToken An OAUTH token.
   */
  public boolean isValid(String userName, String oauthToken) throws SaslException {
    boolean rv = false;
    try {
      TokenInfo tkInfo = BFOAuthAccessor.getTokenInfo(oauthToken);
      LOGGER.info("UserName : {}, TokenInfo : {}", userName, tkInfo);
      if (tkInfo != null) {
        if (tkInfo.isAuthenticated() && userName.equalsIgnoreCase(
            makeNode(tkInfo.getUserName(), tkInfo.getMmxAppId()))) {
          rv = true;
          List<String> roles = tkInfo.getRoles();
          String xid = userName;
          // update the roles for the user in user cache.
          UserRoleCache.cacheRoles(xid, roles);
        } else {
          LOGGER.debug("Token:{} is unauthenticated or invalid. Token Info:{}", oauthToken, tkInfo);
        }
      }
      return rv;
    } catch (Throwable e) {
      LOGGER.warn("Caught exception", e);
      throw new SaslException(e.getMessage());
    }
  }

  // TODO: should use JIDUtil.makeNode().
  private String makeNode(String userId, String appId) {
    return userId+'%'+appId;
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
