# The database update has been implemented in org.jivesoftware.database.bugfix.OF33.java
# Update version
UPDATE ofVersion SET version = 21 WHERE name = 'openfire';

/* Update ofOffline table to increase the username column size */
/* This is required because we are storing the complete JID of the target user in the username column */
ALTER TABLE ofOffline MODIFY COLUMN username varchar(200);

/* Increase the message size to support larger payloads */
ALTER TABLE ofOffline MODIFY COLUMN stanza MEDIUMTEXT;

/* Add packetId column to ofOffline message table */
ALTER TABLE ofOffline ADD packetId varchar(100) after messageId;

delete from ofProperty where name='provider.lockout.className';
insert into ofProperty values ('provider.lockout.className', 'com.magnet.mmx.lockout.MmxLockoutProvider');

delete from ofProperty where name='xmpp.parser.buffer.size';
insert into ofProperty values ('xmpp.parser.buffer.size', '2097152');

delete from ofProperty where name='xmpp.routing.strict';
insert into ofProperty values('xmpp.routing.strict', 'true');

delete from ofProperty where name='xmpp.client.idle';
insert into ofProperty values('xmpp.client.idle', '-1');

delete from ofProperty where name='xmpp.client.idle.ping';
insert into ofProperty values('xmpp.client.idle.ping', 'false');
