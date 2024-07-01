CREATE TABLE IF NOT EXISTS patients (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(100),
    date_of_birth VARCHAR(100),
    zip_code VARCHAR(10),
    gender VARCHAR(10),
    disease VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS anonymized_patient (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(100),
    date_of_birth VARCHAR(100),
    zip_code VARCHAR(10),
    gender VARCHAR(10),
    disease VARCHAR(100)
    );
