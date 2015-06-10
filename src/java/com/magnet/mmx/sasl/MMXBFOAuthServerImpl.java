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

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * MMX Blowfish oauth server implementation.
 *
 */
public class MMXBFOAuthServerImpl implements SaslServer {
  private static Logger LOGGER = LoggerFactory.getLogger(MMXBFOAuthServerImpl.class);

  private String username; //requested authorization identity
  private String oauthToken;
  private CallbackHandler cbh;
  private boolean completed;
  private boolean aborted;
  private int counter;

  public MMXBFOAuthServerImpl(String protocol, String serverFqdn, Map props, CallbackHandler cbh) throws
      SaslException {
    this.cbh = cbh;
    this.completed = false;
    this.counter = 0;
  }

  @Override
  public String getMechanismName() {
    return MMXSaslServerFactoryImpl.MMX_BF_OUATH;
  }

  @Override
  public byte[] evaluateResponse(byte[] response) throws SaslException {
    LOGGER.trace("entered evaluateResponse");

    if (completed) {
      throw new IllegalStateException(getMechanismName() + " authentication already completed");
    }
    if (aborted) {
      throw new IllegalStateException(getMechanismName() + " authentication previously aborted due to error");
    }
    try {
      if (response.length != 0) {
//        byte[] decoded = Base64.decode(response,0, response.length, Base64.DONT_BREAK_LINES);
        String data = new String(response, "UTF8");
        StringTokenizer tokens = new StringTokenizer(data, "\0");
        if (tokens.countTokens() != 2) {
          throw new SaslException("Auth data doesn't contain the expected information");
        }
        String uname = tokens.nextToken();
        oauthToken = tokens.nextToken();

        NameCallback nameCallback = new NameCallback("UserId");
        nameCallback.setName(uname);

        PasswordCallback passwordCallback = new PasswordCallback("OAuthToken" , false);
        passwordCallback.setPassword(oauthToken.toCharArray());

        if (cbh != null) {
          cbh.handle(new Callback[]{nameCallback, passwordCallback});
        }

        BFOAuthTokenValidator validate = getValidator();
        if (validate.isValid(uname, oauthToken)) {
          completed = true;
          username = uname;
        } else {
          throw new SaslException(getMechanismName() + " user not authorized");
        }
      } else {
        //Client gave no initial response
        if (counter++ > 1) {
          throw new SaslException(getMechanismName() + " expects an initial response");
        }
        return null;
      }
    } catch (UnsupportedCallbackException e) {
      LOGGER.warn("UnsupportedCallbackException", e);
      aborted = true;
      throw new SaslException("UTF8 not available on platform", e);
    } catch (UnsupportedEncodingException e) {
      LOGGER.warn("Unsupported encoding exception", e);
      aborted = true;
      throw new SaslException("UTF8 not available on platform", e);
    } catch (IOException e) {
      LOGGER.warn("IOException exception", e);
      aborted = true;
      throw new SaslException(getMechanismName() + "authentication failed for: " + username, e);
    }
    return null;
  }

  @Override
  public boolean isComplete() {
    return completed;
  }

  @Override
  public String getAuthorizationID() {
    return this.username;
  }

  @Override
  public byte[] unwrap(byte[] incoming, int offset, int len) throws SaslException {
    if (completed) {
      throw new IllegalStateException(getMechanismName() + " does not support integrity or privacy");
    } else {
      throw new IllegalStateException(getMechanismName() + " authentication not completed");
    }
  }

  @Override
  public byte[] wrap(byte[] outgoing, int offset, int len) throws SaslException {
    if (completed) {
      throw new IllegalStateException(getMechanismName() + " does not support integrity or privacy");
    } else {
      throw new IllegalStateException(getMechanismName() + " authentication not completed");
    }
  }

  @Override
  public Object getNegotiatedProperty(String propName) {
    LOGGER.trace("Entered: getNegotiatedProperty");
    if (completed) {
      if (propName.equals(Sasl.QOP)) {
        return "auth";
      } else {
        return null;
      }
    } else {
      throw new IllegalStateException(getMechanismName() + " authentication not completed");
    }
  }

  /**
   * Invalidate any state in this Server implementation
   *
   * @throws SaslException
   */
  @Override
  public void dispose() throws SaslException {
    LOGGER.trace("Entered: dispose");
    this.username = null;
    this.oauthToken = null;
    this.completed = false;
    this.counter = 0;
  }

  protected BFOAuthTokenValidator getValidator() {
    return new BFOAuthTokenValidatorImpl();
  }
}
