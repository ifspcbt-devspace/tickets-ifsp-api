CREATE OR REPLACE FUNCTION delete_inactive_users()
    RETURNS void AS $$
BEGIN
    DELETE FROM users
    WHERE active = FALSE
      AND id IN (

        SELECT user_id
        FROM upsert_emails
        WHERE request_date < NOW() - INTERVAL '4 days'
    );
END;
$$ LANGUAGE plpgsql;

BEGIN;
SELECT delete_inactive_users();

SELECT * FROM users;
SELECT * FROM upsert_emails;
ROLLBACK;

