CREATE TABLE kora7392.Catalog (
    No                  SERIAL,
    Name                VARCHAR(50)             NOT NULL CONSTRAINT Catalog_name_constraint CHECK (Name <> ''),
    Type                VARCHAR(6)              NOT NULL CONSTRAINT Catalog_type_constraint CHECK(Type IN ('ALBUM', 'SINGLE', 'EP')) DEFAULT 'SINGLE',

    PRIMARY KEY (No)
);

CREATE TABLE kora7392.Label (
    ID                  CHAR(6)                 NOT NULL CONSTRAINT Label_ID_constraint CHECK (ID <> ''),
    Name                VARCHAR(50)             NOT NULL CONSTRAINT Label_name_constraint CHECK (Name <> ''),
    Establishment_date  DATE                    NOT NULL CONSTRAINT Label_establishment_date_constraint CHECK(Establishment_date BETWEEN '1900-01-01' AND CURRENT_DATE),

    PRIMARY KEY (ID)
);

CREATE TABLE kora7392.Artist (
    ID                  CHAR(10)                NOT NULL CONSTRAINT Artist_ID_constraint CHECK (ID <> ''),
    Pseudonym           VARCHAR(50)             NOT NULL CONSTRAINT Artist_pseudonym_constraint CHECK (Pseudonym <> ''),
    First_name          VARCHAR(50)             NOT NULL CONSTRAINT Artist_first_name_constraint CHECK (First_name <> ''),
    Last_name           VARCHAR(50)             NOT NULL CONSTRAINT Artist_last_name_constraint CHECK (Last_name <> ''),
    Birth_date          DATE                    NOT NULL CONSTRAINT Artist_birth_date_constraint CHECK(Birth_date >= '1900-01-01'),

    PRIMARY KEY (ID)
);

CREATE TABLE kora7392.Record (
    ID                  CHAR(10)                NOT NULL CONSTRAINT Record_ID_constraint CHECK (ID <> ''),
    Title               VARCHAR(100)            NOT NULL CONSTRAINT Record_title_constraint CHECK (Title <> ''),
    Catalog             INTEGER                 NOT NULL,
    Label               CHAR(6)                 NOT NULL, 
    Length              TIME                    NOT NULL CONSTRAINT Record_length_constraint CHECK(Length BETWEEN '00:00:01' AND '23:59:59'),
    Preorder_date       DATE                    NOT NULL CONSTRAINT Record_preorder_date_constraint CHECK(Preorder_date >= '1900-01-01'),
    Release_date        DATE                    NOT NULL,
    Genre               VARCHAR(30)             NOT NULL CONSTRAINT Record_genre_constraint CHECK(Genre IN ('House', 'Tech House', 'Techno', 'Hardstyle')) DEFAULT 'House',
    Price               DECIMAL(4,2)            NOT NULL CONSTRAINT Record_price_constraint CHECK(Price BETWEEN 0.00 AND 99.99),

    CONSTRAINT Record_release_date_constraint   CHECK(Release_date > Preorder_date),

    PRIMARY KEY (ID),

--When the catalog is deleted - all the records are deleted too
    CONSTRAINT Record_foreign_catalog           FOREIGN KEY (Catalog) REFERENCES kora7392.Catalog ON DELETE CASCADE ON UPDATE CASCADE,

--When the label is being deleted - RESTRICT
    CONSTRAINT Record_foreign_label             FOREIGN KEY (Label) REFERENCES kora7392.Label ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE kora7392.RecordArtist (
    Record              CHAR(10)                NOT NULL,
    Artist              CHAR(10)                NOT NULL,

    PRIMARY KEY (Record, Artist),

--When the record is deleted - all the artist-records are deleted too
    CONSTRAINT RecordArtist_foreign_record      FOREIGN KEY (Record) REFERENCES kora7392.Record ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT RecordArtist_foreign_artist      FOREIGN KEY (Artist) REFERENCES kora7392.Artist /*ON DELETE RESTRICT*/ ON UPDATE CASCADE
);

--Check that preorder date and purchase date are not less that the actual record dates
CREATE TABLE kora7392.Order (
    No                  SERIAL,
    Record              CHAR(10)            NOT NULL,
    Buyer               VARCHAR(50)         NOT NULL CONSTRAINT Order_buyer_constraint CHECK (Buyer <> ''),
    Preorder_date       DATE                CONSTRAINT Order_preorder_constraint CHECK(Preorder_date IS NULL OR (Preorder_date BETWEEN '1900-01-01' AND CURRENT_DATE)),
    Purchase_date       DATE,

    CONSTRAINT Order_dates_constraint       CHECK(Preorder_date IS NOT NULL OR Purchase_date IS NOT NULL),
    CONSTRAINT Order_purchase_constraint    CHECK(Purchase_date IS NULL OR Purchase_date > Preorder_date),

    PRIMARY KEY (No),

--When the record is being deleted - delete all its orders too
    CONSTRAINT Order_foreign_record         FOREIGN KEY (Record) REFERENCES kora7392.Record ON DELETE CASCADE ON UPDATE CASCADE
);