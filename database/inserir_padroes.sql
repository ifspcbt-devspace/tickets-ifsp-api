CREATE OR REPLACE PROCEDURE CriarEntidadesPadroes()
AS
$$
DECLARE
    id_usuario uuid;
    id_company uuid;
    id_event   uuid;
BEGIN
    INSERT INTO users (id, name, role_id, username, email, phone, password, cpf, birth_date, active)
    VALUES (uuid_generate_v4(),
            'Leonardo',
            2,
            'c_manager',
            'l.6042silva@gmail.com',
            '11999999999',
            '$2y$10$C2LFrm1SlAx8H13NsZDns.DOj4eHVBz4mfYVwI.MaGKD6/IKAZItG',
            '63640027060',
            '1999-04-02',
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


    id_event := uuid_generate_v4();

    INSERT INTO addresses (id, street, number, complement, neighborhood, city, state, country, zip_code)
    VALUES (id_event,
            'Rua Maria Cristina',
            '50',
            '', -- Assuming complement is optional and nullable
            'Jardim Casqueiro',
            'Cubatão',
            'São Paulo',
            'Brasil',
            '11533160');

    INSERT INTO events (id, name, description, init_date, end_date, address_id, company_id, status)
    VALUES (id_event,
            'Festa Junina',
            'A Festa Junina é uma tradicional celebração brasileira que ocorre em junho, homenageando os santos populares São João, Santo Antônio e São Pedro. Com danças de quadrilha, fogueiras, comidas típicas como milho e quentão, e trajes caipiras, a festa destaca-se pela sua atmosfera alegre e colorida. É comum ver barracas com brincadeiras como pescaria e correio elegante. A celebração também inclui músicas típicas e uma forte influência das tradições rurais do nordeste do Brasil.',
            '2024-07-29',
            '2024-07-30',
            id_event, -- Use the ID of the address created above
            id_company,
            3);

end ;
$$ LANGUAGE plpgsql;

CALL CriarEntidadesPadroes();