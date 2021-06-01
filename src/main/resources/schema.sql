DROP TABLE IF EXISTS audit_entry;
DROP TABLE IF EXISTS application;
DROP TABLE IF EXISTS applicant;
DROP TABLE IF EXISTS vehicle_data;

CREATE TABLE audit_entry (
    id BIGINT NOT NULL,
    log_timestamp TIMESTAMP NOT NULL,
    request TEXT NOT NULL,
    endpoint VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE application (
    id BIGINT NOT NULL,
    reference VARCHAR(255) NOT NULL,
    requested_amount DECIMAL NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE applicant (
    id BIGINT NOT NULL,
    person_code VARCHAR(30) NOT NULL,
    income DECIMAL NOT NULL DEFAULT 0,
    application_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (application_id) REFERENCES application (id)
);

CREATE TABLE vehicle_data (
    id BIGINT NOT NULL,
    vin VARCHAR(17) NOT NULL,
    application_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (application_id) REFERENCES application (id)
);

-- Logback tables, see https://github.com/qos-ch/logback/blob/master/logback-classic/src/main/resources/ch/qos/logback/classic/db/script/h2.sql
DROP TABLE IF EXISTS logging_event_exception;
DROP TABLE IF EXISTS logging_event_property;
DROP TABLE IF EXISTS logging_event;

CREATE TABLE logging_event (
    timestmp BIGINT NOT NULL,
    formatted_message LONGVARCHAR NOT NULL,
    logger_name VARCHAR(256) NOT NULL,
    level_string VARCHAR(256) NOT NULL,
    thread_name VARCHAR(256),
    reference_flag SMALLINT,
    arg0 VARCHAR(256),
    arg1 VARCHAR(256),
    arg2 VARCHAR(256),
    arg3 VARCHAR(256),
    caller_filename VARCHAR(256),
    caller_class VARCHAR(256),
    caller_method VARCHAR(256),
    caller_line CHAR(4),
    event_id IDENTITY NOT NULL
);


CREATE TABLE logging_event_property (
    event_id BIGINT NOT NULL,
    mapped_key  VARCHAR(254) NOT NULL,
    mapped_value LONGVARCHAR,

    PRIMARY KEY(event_id, mapped_key),
    FOREIGN KEY (event_id) REFERENCES logging_event(event_id)
);

CREATE TABLE logging_event_exception (
    event_id BIGINT NOT NULL,
    i SMALLINT NOT NULL,
    trace_line VARCHAR(256) NOT NULL,

    PRIMARY KEY(event_id, i),
    FOREIGN KEY (event_id) REFERENCES logging_event(event_id)
);
