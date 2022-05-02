CREATE VIEW kora7392.Record_releases AS
    SELECT
        R.ID,
        CASE WHEN R.Release_date <= CURRENT_DATE THEN TRUE ELSE FALSE END AS Is_released
    FROM
        Record R;

CREATE VIEW kora7392.Artist_age AS
    SELECT
        A.ID,
        EXTRACT(YEAR FROM AGE(A.Birth_date)) AS Age
    FROM
        Artist A;

CREATE VIEW kora7392.Record_purchases AS
    SELECT
        R.ID,
        R.Label,
        COUNT(*) AS Copies_bought
    FROM
        Record R
    INNER JOIN
        kora7392.Order O
    ON
        R.ID = O.Record
    GROUP BY
        R.ID,
        R.Label;

--Best labels, updated only once in a certain period
CREATE MATERIALIZED VIEW kora7392.Label_purchases AS
    SELECT
        RP.Label,
        L.name,
        SUM(RP.Copies_bought) AS Copies_bought
    FROM
        kora7392.Record_purchases RP 
    INNER JOIN
        kora7392.Label L
    ON
        L.ID = RP.Label
    GROUP BY
        RP.Label,
        L.name
    WITH DATA;