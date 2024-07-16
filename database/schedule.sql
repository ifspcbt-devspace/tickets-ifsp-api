CREATE OR REPLACE FUNCTION delete_inactive_users()
    RETURNS void AS $$
BEGIN
    DELETE FROM users
    WHERE active = FALSE
      AND id IN (
        SELECT user_id
        FROM upsert_emails
        WHERE request_date < NOW() - INTERVAL '2 hours'
    );
END;
$$ LANGUAGE plpgsql;

BEGIN;
CALL delete_inactive_users();
ROLLBACK;

