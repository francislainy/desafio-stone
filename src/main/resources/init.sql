SET SESSION vars.product_id = 'd3256c76-62d7-4481-9d1c-a0ccc4da380f';

INSERT INTO products(id, title, zipcode, seller, thumbnail, date)
VALUES (current_setting('vars.product_id')::uuid, 'Blusa do Imperio', '78993-000', 'João da Silva',
        'https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg', '2023-12-03')
