CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE OR REPLACE PROCEDURE CriarEntidadesPadroes()
AS
$$
DECLARE
    id_usuario uuid;
    id_company uuid;
BEGIN
    INSERT INTO users (id, name, role_id, username, email, phone, password, document, birth_date, active)
    VALUES (uuid_generate_v4(),
            'Grêmio Estudantil Chico Mendes',
            2,
            'c_manager',
            'ifspcbt.informatica@gmail.com',
            '11999999999',
            '$2a$12$P8X9u6OK8rSvNl6Rn3xfkemTTx3cacvxlpFbV9HUnbRXWlGTTS1Pa',
            '/fBJq8d8esoxnRAShHKe7d1X9UdUQGXp',
            '1990-05-30',
            true)
    RETURNING id INTO id_usuario;

    id_company := uuid_generate_v4();

    INSERT INTO addresses (id, street, number, complement, neighborhood, city, state, country, zip_code)
    VALUES (id_company,
            'Rua Maria Cristina',
            '50',
            '', -- Assuming complement is optional and nullable
            'Jardim Casqueiro',
            'Cubatão',
            'São Paulo',
            'Brasil',
            '11533160');

    INSERT INTO companies (id, name, description, cnpj, owner_id, address_id)
    VALUES (id_company,
            'IFSP Cubatão',
            'Instituto Federal de Educação, Ciência e Tecnologia de São Paulo',
            '10882594000327',
            id_usuario,
            id_company);

end ;
$$ LANGUAGE plpgsql;

CALL CriarEntidadesPadroes();