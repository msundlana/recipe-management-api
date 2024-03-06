/*
  Enter custom T-SQL here that would run after PostgresSQL has started up.
  We shouldn't have to do too much here since the ORM will manage the creation of tables, etc.
*/

IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'recipe_db')
BEGIN
  CREATE DATABASE recipe_db;
END;
GO

