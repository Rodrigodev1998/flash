create table profile (
	    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
        username TEXT NOT NULL UNIQUE,
        password TEXT NOT NULL,
        role SMALLINT NOT NULL
);