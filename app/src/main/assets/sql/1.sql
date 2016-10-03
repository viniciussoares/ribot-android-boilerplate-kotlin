-- Ribots Profile

CREATE TABLE ribotsProfile (
  email TEXT PRIMARY KEY,
  firstName TEXT NOT NULL,
  lastName TEXT NOT NULL,
  dateOfBirth INTEGER NOT NULL,
  hexColor TEXT NOT NULL,
  avatar TEXT,
  bio TEXT
);
