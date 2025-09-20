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