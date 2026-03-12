| ID | Tipo | Requisito | Descrição detalhada
|---|---|---|---|---|
| RF-01 | Funcional | Cadastro de funcionários | O sistema deve permitir o cadastro de funcionários para acesso à aplicação.
| RF-02 | Funcional | Login por NIF e senha | O sistema deve permitir autenticação de funcionários usando NIF e senha.
| RF-03 | Funcional | Restrição de acesso por NIF autorizado | O sistema deve permitir criar conta apenas para funcionários previamente autorizados na base de pré-cadastro.
| RF-04 | Funcional | Controle de status do funcionário | O sistema deve permitir marcar funcionário como ativo ou inativo para controlar o acesso ao sistema.
| RF-05 | Funcional | Gerenciamento de materiais e categorias | O sistema deve permitir cadastrar, visualizar, atualizar e excluir materiais e suas categorias.
| RF-06 | Funcional | Movimentações de estoque | O sistema deve permitir registrar entradas e saídas de materiais no estoque.
| RF-07 | Funcional | Histórico de movimentações | O sistema deve armazenar e consultar o histórico de movimentações de estoque.
| RF-08 | Funcional | Visualização de inventário | O sistema deve permitir visualizar o inventário de materiais em estoque.
| RF-09 | Funcional | Gerenciamento de ativos patrimoniais | O sistema deve permitir adicionar, visualizar, atualizar e excluir ativos patrimoniais da instituição.
| RF-10 | Funcional | Gestão de ativos patrimoniais | O sistema deve permitir registrar e consultar os dados dos ativos patrimoniais cadastrados.
| RF-11 | Funcional | Interface de gerenciamento | A interface deve permitir cadastrar materiais, registrar movimentações, visualizar inventário e gerenciar ativos patrimoniais.
| RF-12 | Funcional | Página inicial do sistema | O sistema deve disponibilizar uma tela inicial com acesso a login e cadastro.
| RF-13 | Funcional | Área interna autenticada | O sistema deve possuir uma área interna acessível apenas após login.
| RF-14 | Funcional | Logout | O sistema deve permitir encerrar a sessão do usuário autenticado.
| RF-15 | Funcional | API de gerenciamento | A aplicação deve disponibilizar operações para cadastrar, listar, atualizar e excluir materiais, categorias, movimentações e ativos patrimoniais.
| RF-16 | Funcional | Persistência em banco de dados | O sistema deve armazenar materiais, categorias, movimentações, funcionários e ativos patrimoniais em banco de dados.
| RF-17 | Funcional | Relacionamento entre entidades | O banco deve relacionar materiais, categorias, movimentações e ativos de forma coerente.
| RF-18 | Funcional | Unicidade do NIF | O sistema deve garantir que o NIF do funcionário seja único no banco.
| RF-19 | Funcional | Validação e retorno no acesso | O sistema deve validar o login e o cadastro de funcionários, exibindo mensagens de erro ou sucesso conforme a operação realizada.
| RNF-01 | Não funcional | Identidade visual SENAI-SP | A interface deve seguir o Manual de Identidade Visual do SENAI-SP, utilizando corretamente cores, tipografia, logo e aplicação da marca nas telas do sistema.
| RNF-02 | Não funcional | Usabilidade | A interface deve ser intuitiva, amigável e fácil de usar.
| RNF-03 | Não funcional | Responsividade | A interface deve ser responsiva para diferentes tamanhos de tela.
| RNF-04 | Não funcional | Segurança de acesso | Apenas funcionários autorizados podem acessar o sistema.
| RNF-05 | Não funcional | Segurança de sessão | O sistema deve proteger áreas internas contra acesso sem autenticação.
| RNF-06 | Não funcional | Integridade dos dados | O sistema deve garantir consistência entre materiais, categorias, movimentações e ativos patrimoniais.