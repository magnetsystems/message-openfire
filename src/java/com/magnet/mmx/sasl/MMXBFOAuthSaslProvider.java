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

import java.security.Provider;

public class MMXBFOAuthSaslProvider extends Provider{

  public MMXBFOAuthSaslProvider() {
    super("MagnetSotftware", 1.0, "Magnet SASL provider v1.0, implementing server mechanisms for: X-MMX_BF_OAUTH2");
    put("SaslServerFactory." + MMXSaslServerFactoryImpl.MMX_OAUTH2, "com.magnet.mmx.sasl.MMXSaslServerFactoryImpl");
  }
}
