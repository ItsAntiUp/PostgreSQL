--Indexes
DROP INDEX              IF EXISTS kora7392.Record_title                         CASCADE;
DROP INDEX              IF EXISTS kora7392.Artist_pseudonym                     CASCADE;
DROP INDEX              IF EXISTS kora7392.Order_info                           CASCADE;

--Materialized views
DROP MATERIALIZED VIEW  IF EXISTS kora7392.Label_purchases                      CASCADE;

--Views
DROP VIEW               IF EXISTS kora7392.Record_releases                      CASCADE;
DROP VIEW               IF EXISTS kora7392.Artist_age                           CASCADE;
DROP VIEW               IF EXISTS kora7392.Record_purchases                     CASCADE;

--Triggers
DROP TRIGGER            IF EXISTS Artist_count             ON kora7392.Artist   CASCADE;
DROP TRIGGER            IF EXISTS Artist_birth_date        ON kora7392.Artist   CASCADE;
DROP TRIGGER            IF EXISTS Record_ID                ON kora7392.Record   CASCADE;

--Tables
DROP TABLE              IF EXISTS kora7392.Order                                CASCADE;
DROP TABLE              IF EXISTS kora7392.RecordArtist                         CASCADE;
DROP TABLE              IF EXISTS kora7392.Record                               CASCADE;
DROP TABLE              IF EXISTS kora7392.Catalog                              CASCADE;
DROP TABLE              IF EXISTS kora7392.Label                                CASCADE;
DROP TABLE              IF EXISTS kora7392.Artist                               CASCADE;