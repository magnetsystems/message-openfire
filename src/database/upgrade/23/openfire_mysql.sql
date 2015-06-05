UPDATE ofVersion SET version = 23 WHERE name = 'openfire';

INSERT INTO ofProperty (name, propValue) VALUES( 'xmpp.auth.anonymous', 'false') ON DUPLICATE KEY UPDATE name=VALUES(name), propValue=VALUES(propValue);

INSERT INTO ofProperty (name, propValue) VALUES( 'register.inband', 'false') ON DUPLICATE KEY UPDATE name=VALUES(name), propValue=VALUES(propValue);

INSERT INTO ofProperty (name, propValue) VALUES( 'xmpp.server.socket.active', 'false') ON DUPLICATE KEY UPDATE name=VALUES(name), propValue=VALUES(propValue);


