CREATE        INDEX Record_title        ON kora7392.Record(Title);
CREATE        INDEX Artist_pseudonym    ON kora7392.Artist(Pseudonym);

CREATE UNIQUE INDEX Order_info          ON kora7392.Order(Record, Buyer);