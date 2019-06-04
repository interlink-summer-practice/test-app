alter table topics alter column url type character varying using url::character varying;

alter table topics alter column url drop not null;