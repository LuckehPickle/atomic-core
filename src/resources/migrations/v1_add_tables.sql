CREATE TABLE player (
    player_id     UUID        PRIMARY KEY NOT NULL UNIQUE,
    username      VARCHAR(16) NOT NULL,
    last_seen     TIMESTAMP   NOT NULL,
    login_count   INT NOT     NULL,
    message_count INT NOT     NULL,
    warning_count INT NOT     NULL
);

CREATE TABLE warp (
    warp_id UUID           PRIMARY KEY NOT NULL UNIQUE,
    name    VARCHAR(16)    NOT NULL,
    message VARCHAR(32),
    world   UUID           NOT NULL,
    x       NUMERIC(16, 3) NOT NULL,
    y       NUMERIC(16, 3) NOT NULL,
    z       NUMERIC(16, 3) NOT NULL,
    pitch   NUMERIC(16, 3) NOT NULL,
    yaw     NUMERIC(16, 3) NOT NULL
)