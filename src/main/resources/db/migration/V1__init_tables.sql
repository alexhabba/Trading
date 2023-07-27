CREATE TABLE bar
(
    id          uuid      NOT NULL,
    "close"     float8    NOT NULL,
    high        float8    NOT NULL,
    "interval"  int4      NOT NULL,
    low         float8    NOT NULL,
    "open"      float8    NOT NULL,
    volume_buy  int4      NOT NULL,
    volume_sell int4      NOT NULL,
    symbol      varchar   NOT NULL,
    date_time   timestamp NOT NULL,
    CONSTRAINT bar_pkey PRIMARY KEY (id)
);

CREATE TABLE bar_price
(
    id          uuid   NOT NULL,
    price       float8 NOT NULL,
    volume_buy  int4   NOT NULL,
    volume_sell int4   NOT NULL,
    bar_id      uuid   NULL,
    CONSTRAINT bar_price_pkey PRIMARY KEY (id)
);

ALTER TABLE bar_price
    ADD CONSTRAINT bar_price_fk FOREIGN KEY (bar_id) REFERENCES bar (id);