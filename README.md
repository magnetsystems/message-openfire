Magnet Message Openfire
========

About
-----
Message Openfire is a customized version of [Openfire](https://github.com/igniterealtime/Openfire) an XMPP server supported by Ignite Realitime community.
It is based on Openfire version 3.9.3. Following is a list of some of the customizations:

- Store offline messages using complete JIDs
- Add support for headless installation
- Customize the console look and feel
- Fix SQL syntax error related to Pub/Sub implementation

Please refer to Openfire [Readme.md](https://github.com/igniterealtime/Openfire/blob/master/README.md) for information about Openfire.

### Note
To avail of all Magnet Message server features, you would also need to build Magnet's openfire plugin. Please refer to [message-server](https://github.com/magnetsystems/message-server)
repository for instructions to build the plugin and Magnet Message server installation packages.

### Build Instructions
----------------------

#### Pre-requisites
- JDK 1.6 or above.
- Maven 3 or above.

#### Build
1. `git clone https://github.com/magnetsystems/message-openfire`
2. `mvn clean install`

