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

import org.jivesoftware.openfire.sasl.PolicyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;
import javax.security.sasl.SaslServerFactory;
import java.util.Map;

/**
 * MMX Sasl Server Factory implementation that provides a SaslServer that can authenticate
 * oauth tokens using blowfish authenticate API.
 */
public class MMXSaslServerFactoryImpl implements SaslServerFactory {
  private static Logger LOGGER = LoggerFactory.getLogger(MMXSaslServerFactoryImpl.class);

  public static final String MMX_BF_OUATH = "X-MMX_BF_OAUTH2";
  private static final String myMechs[] = {MMX_BF_OUATH};
  private static final int mechPolicies[] = { PolicyUtils.NOANONYMOUS};

  @Override
  public SaslServer createSaslServer(String mechanism, String protocol, String serverName, Map<String, ?> props, CallbackHandler cbh)
      throws SaslException {
    LOGGER.trace("entered createSaslServer");
    if (mechanism.equals(MMX_BF_OUATH) && PolicyUtils.checkPolicy(PolicyUtils.NOANONYMOUS, props)) {
      if (cbh == null) {
        throw new SaslException("CallbackHandler with support for Password, Name, and AuthorizeCallback required");
      }
      return new MMXBFOAuthServerImpl(protocol, serverName, props, cbh);
    }
    return null;
  }

  @Override
  public String[] getMechanismNames(Map<String, ?> props) {
    return PolicyUtils.filterMechs(myMechs, mechPolicies, props);
  }
}
