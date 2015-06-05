UPDATE ofVersion SET version = 22 WHERE name = 'openfire';

INSERT INTO ofProperty (name, propValue) VALUES( 'xmpp.pubsub.flush.max', '-1') ON DUPLICATE KEY UPDATE name=VALUES(name), propValue=VALUES(propValue);
