ALTER TABLE players
    DROP COLUMN message_count;
ALTER TABLE players
    ADD COLUMN verbosity SMALLINT NOT NULL DEFAULT 0;