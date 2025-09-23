Funcionalidade: Gerenciamento de promoções
  Como um administrador do sistema
  Eu quero poder criar novas promoções
  Para que os clientes possam usá-las em suas compras

  Cenário: Criar uma nova promoção com desconto de 10%
    Dado que estou na página de criação de promoções
    E eu preencho o campo "Código" com "PROMO10"
    E eu preencho o campo "Desconto" com "10%"
    Quando eu clico no botão "Salvar"
    Então a nova promoção deve ser listada na tabela
  Cenário: Tentativa de criar uma nova promoção sem código
    Dado que estou na página de criação de promoções
    E eu preencho o campo "Desconto" com "10%"
    Quando eu clico no botão "Salvar"
    Então uma mensagem de erro deve ser exibida informando que o campo "Código" é obrigatório
    E a nova promoção não deve ser listada na tabela
  Cenário: Tentativa de criar uma nova promoção com um código já existente
    Dado que já existe uma promoção com o código "PROMO10"
    E eu estou na página de criação de promoções
    E eu preencho o campo "Código" com "PROMO10"
    E eu preencho o campo "Desconto" com "5%"
    Quando eu clico no botão "Salvar"
    Então uma mensagem de erro deve ser exibida informando que o código já está em uso
    E a nova promoção não deve ser listada na tabela
  Cenário: Criar uma nova promoção com desconto de 20%
    Dado que estou na página de criação de promoções
    E eu preencho o campo "Código" com "PROMO20"
    E eu preencho o campo "Desconto" com "20%"
    Quando eu clico no botão "Salvar"
    Então a nova promoção deve ser listada na tabela
  Cenário: Criar uma nova promoção com desconto de 15%
    Dado que estou na página de criação de promoções
    E eu preencho o campo "Código" com "PROMO15"
    E eu preencho o campo "Desconto" com "15%"
    Quando eu clico no botão "Salvar"
    Então a nova promoção deve ser listada na tabela
  Cenário: Verificar que apenas uma promoção com código existente é listada
    Dado que já existe uma promoção com o código "PROMO10"
    E eu estou na página de promoções
    Então apenas uma promoção com o código "PROMO10" deve ser listada na tabela
  Cenário: Listar todas as promoções existentes
    Dado que existem promoções com os códigos "PROMO10", "PROMO20
    E eu estou na página de promoções
    Então todas as promoções existentes devem ser listadas na tabela
    E a tabela deve conter exatamente 2 promoções
  Cenário: Verificar que a promoção com código "PROMO10" tem desconto de 10%
    Dado que já existe uma promoção com o código "PROMO10" e desconto de
    E eu estou na página de promoções
    Então a promoção com o código "PROMO10" deve ter um desconto de "10
    E a tabela deve listar a promoção com o código "PROMO10" e desconto de "10%"
    E a tabela deve conter exatamente 1 promoção com o código "PROMO10"
    E a tabela deve conter exatamente 1 promoção com desconto de "10%"
    
    