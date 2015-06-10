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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.sasl.SaslException;

public class BFOAuthTokenValidatorImpl implements BFOAuthTokenValidator {
  private static Logger LOGGER = LoggerFactory.getLogger(BFOAuthTokenValidatorImpl.class);

  private final String VALID_TOKEN = "78647oUtyre";

  @Override
  public boolean isValid(String userId, String oauthToken) throws SaslException {
    LOGGER.trace("entered isValid");
    boolean rv = VALID_TOKEN.equalsIgnoreCase(oauthToken);
    return rv;
  }
}
