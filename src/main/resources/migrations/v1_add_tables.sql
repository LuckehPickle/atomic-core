CREATE TABLE players (
    player_id     UUID        PRIMARY KEY NOT NULL UNIQUE,
    username      VARCHAR(16) NOT NULL,
    level         INTEGER     NOT NULL,
    experience    INTEGER     NOT NULL,
    last_seen     TIMESTAMP   NOT NULL,
    login_count   INTEGER     NOT NULL,
    message_count INTEGER     NOT NULL,
    warning_count INTEGER     NOT NULL
);

CREATE TABLE warps (
    warp_id  UUID         PRIMARY KEY NOT NULL UNIQUE,
    name     VARCHAR(16)  NOT NULL UNIQUE,
    message  VARCHAR(32),
    world_id UUID         NOT NULL,
    x        INTEGER      NOT NULL,
    y        INTEGER      NOT NULL,
    z        INTEGER      NOT NULL,
    pitch    NUMERIC      NOT NULL,
    yaw      NUMERIC      NOT NULL
)