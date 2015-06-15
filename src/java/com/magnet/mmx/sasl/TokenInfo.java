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

import java.util.Collections;
import java.util.List;

/**
 * {
 "anonymous": false,
 "authenticated": true,
 "clientId": "af1c90d4-d9fa-449b-b190-831832c8261e",
 "deviceId": "1111-2222-3333-4444",
 "roles": [
 “APPUSERROLE1",
 “USER"
 ],
 "tokenCreationTime": 1434293806727,
 "tokenExpiresIn": 7200,
 "userId": "dacbf150-fa13-4579-8562-89a2436ac090",
 "userName": "testuser”
 }
 */
public class TokenInfo {

  private boolean anonymous;
  private boolean authenticated;
  private String clientId;
  private String deviceId;
  private List<String> roles = Collections.emptyList();
  private Long tokenCreationTime;
  private Integer tokenExpiresIn;
  private String userId;
  private String userName;

  public boolean isAnonymous() {
    return anonymous;
  }

  public void setAnonymous(boolean anonymous) {
    this.anonymous = anonymous;
  }

  public boolean isAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public Long getTokenCreationTime() {
    return tokenCreationTime;
  }

  public void setTokenCreationTime(Long tokenCreationTime) {
    this.tokenCreationTime = tokenCreationTime;
  }

  public Integer getTokenExpiresIn() {
    return tokenExpiresIn;
  }

  public void setTokenExpiresIn(Integer tokenExpiresIn) {
    this.tokenExpiresIn = tokenExpiresIn;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TokenInfo{");
    sb.append("anonymous=").append(anonymous);
    sb.append(", authenticated=").append(authenticated);
    sb.append(", clientId='").append(clientId).append('\'');
    sb.append(", deviceId='").append(deviceId).append('\'');
    sb.append(", roles=").append(roles);
    sb.append(", tokenCreationTime=").append(tokenCreationTime);
    sb.append(", tokenExpiresIn=").append(tokenExpiresIn);
    sb.append(", userId='").append(userId).append('\'');
    sb.append(", userName='").append(userName).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
