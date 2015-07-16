UPDATE ofVersion SET version = 24 WHERE name = 'openfire';

INSERT INTO ofProperty (name, propValue) VALUES( 'flash.crossdomain.enabled', 'false') ON DUPLICATE KEY UPDATE name=VALUES(name), propValue=VALUES(propValue);

INSERT INTO ofProperty (name, propValue) VALUES( 'xmpp.proxy.enabled', 'false') ON DUPLICATE KEY UPDATE name=VALUES(name), propValue=VALUES(propValue);

