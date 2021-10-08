ALTER TABLE public.users
  ADD password VARCHAR(250);


INSERT INTO public.users (id, phone, email, first_name, second_name, patronymic, address_id, company_id,password)
VALUES (60000, 70000000000, 'user0@mail.ru', 'Петр', 'Петров', 'Петрович', 80002, 50000,11111);
