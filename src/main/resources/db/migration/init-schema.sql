create sequence  IF NOT EXISTS blog_seq START with 1 INCREMENT BY 1;

create TABLE blog (
  id INTEGER NOT NULL,
   title VARCHAR(255),
   publish_date date NOT NULL,
   CONSTRAINT pk_blog PRIMARY KEY (id)
);

create TABLE content (
  blog_id INTEGER NOT NULL,
   position INTEGER NOT NULL,
   type VARCHAR(255),
   value TEXT,
   CONSTRAINT pk_content PRIMARY KEY (blog_id, position)
);