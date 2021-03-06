CREATE TABLE players (
    player_id     UUID PRIMARY KEY NOT NULL UNIQUE,
    username      VARCHAR(16)      NOT NULL,
    display_name  VARCHAR(24),
    level         INTEGER          NOT NULL,
    experience    INTEGER          NOT NULL,
    last_seen     TIMESTAMP        NOT NULL,
    login_count   INTEGER          NOT NULL,
    verbosity     SMALLINT         NOT NULL DEFAULT 0
);

CREATE TABLE warps (
    warp_id  UUID PRIMARY KEY NOT NULL UNIQUE,
    name     VARCHAR(16)      NOT NULL UNIQUE,
    message  VARCHAR(64),
    world_id UUID             NOT NULL,
    x        INTEGER          NOT NULL,
    y        INTEGER          NOT NULL,
    z        INTEGER          NOT NULL,
    pitch    NUMERIC          NOT NULL,
    yaw      NUMERIC          NOT NULL
);