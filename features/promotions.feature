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