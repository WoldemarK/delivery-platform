CREATE TABLE products
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(200)   NOT NULL,
    description VARCHAR(2000),
    price       NUMERIC(12, 2) NOT NULL,
    currency    CHAR(3)        NOT NULL,
    active      BOOLEAN        NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version     BIGINT         NOT NULL DEFAULT 0,

    CONSTRAINT chk_products_name_not_blank CHECK (LENGTH(TRIM(name)) > 0),
    CONSTRAINT chk_products_price_non_negative CHECK (price >= 0),
    CONSTRAINT chk_products_currency_format CHECK (currency ~ '^[A-Z]{3}$'),
    CONSTRAINT chk_products_version_non_negative CHECK (version >= 0)
);

CREATE INDEX idx_products_active ON products (active);
CREATE INDEX idx_products_name ON products (name);


CREATE TABLE product_price_history
(
    id         UUID PRIMARY KEY,
    product_id UUID           NOT NULL,
    old_price  NUMERIC(12, 2) NOT NULL,
    new_price  NUMERIC(12, 2) NOT NULL,
    currency   CHAR(3)        NOT NULL,
    changed_at TIMESTAMPTZ    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    changed_by VARCHAR(100),

    CONSTRAINT fk_product_price_history_product
        FOREIGN KEY (product_id)
            REFERENCES products (id)
            ON DELETE RESTRICT,

    CONSTRAINT chk_price_history_old_price CHECK (old_price >= 0),

    CONSTRAINT chk_price_history_new_price CHECK (new_price >= 0),

    CONSTRAINT chk_price_history_currency_format CHECK (currency ~ '^[A-Z]{3}$'),

    CONSTRAINT chk_price_history_prices_different CHECK (old_price <> new_price)
);

CREATE INDEX idx_product_price_history_product_changed_at ON product_price_history (product_id, changed_at DESC, id DESC);


-- Currency is immutable after product creation.
CREATE FUNCTION prevent_product_currency_change() RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NEW.currency IS DISTINCT FROM OLD.currency THEN
        RAISE EXCEPTION 'Product currency cannot be changed';
    END IF;

    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_prevent_product_currency_change
    BEFORE UPDATE OF currency
    ON products
    FOR EACH ROW
EXECUTE FUNCTION prevent_product_currency_change();


-- Price history is append-only.
CREATE FUNCTION prevent_product_price_history_modification()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    RAISE EXCEPTION 'Product price history is append-only';
END;
$$;

CREATE TRIGGER trg_prevent_product_price_history_modification
    BEFORE UPDATE OR DELETE
    ON product_price_history
    FOR EACH ROW
EXECUTE FUNCTION prevent_product_price_history_modification();