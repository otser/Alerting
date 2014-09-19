INSERT INTO alerting.eventaction(id,actionType,eventType) VALUES(1, 'LOG_TO_DB', 'INFO');
INSERT INTO alerting.eventaction(id,actionType,eventType) VALUES(2, 'LOG_TO_DB', 'WARNING');
INSERT INTO alerting.eventaction(id,actionType,eventType) VALUES(3, 'LOG_TO_DB', 'EXCEPTION');


truncate table alerting.logentry;