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

import javax.security.sasl.SaslException;

public interface BFOAuthTokenValidator {

  /**
   * Check if the supplied oauth token is valid.
   * @param userId supplied userId. Can be null.
   * @param oauthToken supplied oauth token. Can't be null.
   * @return true if the token is valid. False other wise.
   * @throws SaslException if validation can't be performed.
   */
  boolean isValid (String userId, String oauthToken) throws SaslException;

}
