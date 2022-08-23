DBNAME?=mcullenmdev
DBPASS?=postgres

run-db:
	docker run --name mcullenm_dev_db -p 5432:5432 -e POSTGRES_PASSWORD=$(DBPASS) -e POSTGRES_DB=$(DBNAME) -v ${PWD}/db_data:/var/lib/postgresql/data -d postgres