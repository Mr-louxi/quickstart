
CREATE TABLE TestSqlSource_Gatewaylog (
  "remote_addr" VARCHAR,
  "remote_user" VARCHAR,
  "time_iso8601" VARCHAR,
  "request" VARCHAR,
  "status" VARCHAR,
  "body_bytes_sent" VARCHAR,
  "upstream_response_time" VARCHAR,
  "upstream_addr" VARCHAR,
  "http_referer" VARCHAR,
  "http_user_agent" VARCHAR,
  "http_x_forwarded_for" VARCHAR,
  "request_uri" VARCHAR
)WITH (
  'connector.type' = 'kafka',
  'connector.version' = 'universal',
  'connector.topic' = 'gatewaylog_topic',
  'update-mode' = 'append',
  'connector.properties.0.key' = 'zookeeper.connect',
  'connector.properties.0.value' = '192.168.31.55:2181',
  'connector.properties.1.key' = 'bootstrap.servers',
  'connector.properties.1.value' = '192.168.31.55:9092',
  'connector.startup-mode' = 'earliest-offset',
  'format.type' = 'json',
  'format.derive-schema' = 'true'
);



CREATE TABLE Sink_Sensor_soilWS (
  "Time" DATE ,
  "soil_temperature" VARCHAR,
  "soil_moisture" VARCHAR,
  "type" VARCHAR,
  "subType" VARCHAR,
)WITH (
  'connector.type' = 'Elasticsearch',
  'connector.version' = 'universal',
  'connector.topic' = 'Sink_Sensor_soilWS',
  'update-mode' = 'append',
  'connector.properties.0.key' = 'es',
  'connector.properties.0.value' = '192.168.31.55:9200',
  'format.type' = 'json',
  'format.derive-schema' = 'true'
);


