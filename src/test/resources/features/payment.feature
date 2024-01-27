#language: pt

Funcionalidade: Gerador de QR-CODE

  Cenario: Gerar QR-CODE
    Quando realizar uma requisição para o endpoint de geração de QR-CODE com dados válidos
    Então o código de status da resposta deve ser 200
    E a resposta deve conter o QR-CODE