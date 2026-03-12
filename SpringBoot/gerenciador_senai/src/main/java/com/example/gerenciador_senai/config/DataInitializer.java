package com.example.gerenciador_senai.config;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.gerenciador_senai.model.AtivoPatrimonial;
import com.example.gerenciador_senai.model.Categoria;
import com.example.gerenciador_senai.model.Funcionario;
import com.example.gerenciador_senai.model.FuncionarioAutenticado;
import com.example.gerenciador_senai.model.Material;
import com.example.gerenciador_senai.model.StatusAtivo;
import com.example.gerenciador_senai.model.TipoMovimentacao;
import com.example.gerenciador_senai.repository.CategoriaRepository;
import com.example.gerenciador_senai.repository.FuncionarioAutenticadoRepository;
import com.example.gerenciador_senai.repository.FuncionarioRepository;
import com.example.gerenciador_senai.repository.MaterialRepository;
import com.example.gerenciador_senai.repository.MovimentacaoEstoqueRepository;
import com.example.gerenciador_senai.service.AtivoPatrimonialService;
import com.example.gerenciador_senai.service.MovimentacaoEstoqueService;

@Configuration
public class DataInitializer {

	// cria dados iniciais para facilitar os testes
	@Bean
	CommandLineRunner initData(FuncionarioAutenticadoRepository funcionarioAutenticadoRepository,
			FuncionarioRepository funcionarioRepository,
			CategoriaRepository categoriaRepository,
			MaterialRepository materialRepository,
			MovimentacaoEstoqueRepository movimentacaoEstoqueRepository,
			MovimentacaoEstoqueService movimentacaoEstoqueService,
			AtivoPatrimonialService ativoPatrimonialService) {
		return args -> {
			// cria os nifs liberados para cadastro
			// o nome e o nif daqui precisam ser usados iguais na tela de cadastro
			salvarFuncionarioAutorizado(funcionarioAutenticadoRepository, "Administrador Senai", "1001");
			salvarFuncionarioAutorizado(funcionarioAutenticadoRepository, "Tecnico Laboratorio", "1002");
			// para liberar outro nif, adicione outra chamada igual aqui
			// exemplo: salvarFuncionarioAutorizado(funcionarioAutenticadoRepository, "Novo Nome", "1003");

			// cria o usuario inicial para o primeiro login
			if (funcionarioRepository.count() == 0) {
				Funcionario funcionario = new Funcionario();
				funcionario.setNome("Administrador Senai");
				funcionario.setNif("1001");
				funcionario.setSenha("1234");
				funcionario.setAtivo(true);
				funcionarioRepository.save(funcionario);
			}

			// cria categorias iniciais
			if (categoriaRepository.count() == 0) {
				Categoria informatica = new Categoria();
				informatica.setNome("Informatica");
				informatica.setDescricao("equipamentos e perifericos");
				categoriaRepository.save(informatica);

				Categoria laboratorio = new Categoria();
				laboratorio.setNome("Laboratorio");
				laboratorio.setDescricao("materiais de uso tecnico");
				categoriaRepository.save(laboratorio);

				Categoria limpeza = new Categoria();
				limpeza.setNome("Limpeza");
				limpeza.setDescricao("itens de apoio e higienizacao");
				categoriaRepository.save(limpeza);
			}

			// cria materiais iniciais
			if (materialRepository.count() == 0) {
				Categoria informatica = categoriaRepository.findAllByOrderByNomeAsc().stream()
						.filter(categoria -> categoria.getNome().equalsIgnoreCase("Informatica"))
						.findFirst()
						.orElseThrow();
				Categoria laboratorio = categoriaRepository.findAllByOrderByNomeAsc().stream()
						.filter(categoria -> categoria.getNome().equalsIgnoreCase("Laboratorio"))
						.findFirst()
						.orElseThrow();

				Material notebook = new Material();
				notebook.setNome("Notebook");
				notebook.setDescricao("notebook para uso em sala");
				notebook.setUnidadeMedida("unidade");
				notebook.setQuantidadeMinima(3);
				notebook.setQuantidadeEmEstoque(0);
				notebook.setCategoria(informatica);
				materialRepository.save(notebook);

				Material multimetro = new Material();
				multimetro.setNome("Multimetro");
				multimetro.setDescricao("equipamento de medicao");
				multimetro.setUnidadeMedida("unidade");
				multimetro.setQuantidadeMinima(2);
				multimetro.setQuantidadeEmEstoque(0);
				multimetro.setCategoria(laboratorio);
				materialRepository.save(multimetro);
			}

			// cria entradas iniciais no estoque
			if (movimentacaoEstoqueRepository.count() == 0) {
				Material notebook = materialRepository.findAllByOrderByNomeAsc().stream()
						.filter(material -> material.getNome().equalsIgnoreCase("Notebook"))
						.findFirst()
						.orElseThrow();
				Material multimetro = materialRepository.findAllByOrderByNomeAsc().stream()
						.filter(material -> material.getNome().equalsIgnoreCase("Multimetro"))
						.findFirst()
						.orElseThrow();

				movimentacaoEstoqueService.salvar(null, notebook.getId(), TipoMovimentacao.ENTRADA, 10,
						"entrada inicial do sistema", "seed inicial");
				movimentacaoEstoqueService.salvar(null, multimetro.getId(), TipoMovimentacao.ENTRADA, 5,
						"entrada inicial do sistema", "seed inicial");
			}

			// cria ativos patrimoniais iniciais
			if (ativoPatrimonialService.contarAtivos() == 0) {
				AtivoPatrimonial projetor = new AtivoPatrimonial();
				projetor.setCodigoPatrimonio("PAT-001");
				projetor.setNome("Projetor Epson");
				projetor.setDescricao("projetor da sala multimidia");
				projetor.setLocalizacao("Sala multimidia");
				projetor.setResponsavel("Coordenacao");
				projetor.setStatusAtivo(StatusAtivo.EM_USO);
				projetor.setDataAquisicao(LocalDate.of(2024, 2, 10));
				projetor.setValorAquisicao(new BigDecimal("4200.00"));
				ativoPatrimonialService.salvar(projetor);

				AtivoPatrimonial bancada = new AtivoPatrimonial();
				bancada.setCodigoPatrimonio("PAT-002");
				bancada.setNome("Bancada de eletronica");
				bancada.setDescricao("bancada principal do laboratorio");
				bancada.setLocalizacao("Laboratorio 2");
				bancada.setResponsavel("Tecnico Laboratorio");
				bancada.setStatusAtivo(StatusAtivo.DISPONIVEL);
				bancada.setDataAquisicao(LocalDate.of(2023, 8, 15));
				bancada.setValorAquisicao(new BigDecimal("3500.00"));
				ativoPatrimonialService.salvar(bancada);
			}
		};
	}

	// salva um nif autorizado se ele ainda nao existir
	private void salvarFuncionarioAutorizado(FuncionarioAutenticadoRepository funcionarioAutenticadoRepository,
			String nome, String nif) {
		if (funcionarioAutenticadoRepository.findByNifAndAtivoTrue(nif).isPresent()) {
			return;
		}

		FuncionarioAutenticado funcionarioAutenticado = new FuncionarioAutenticado();
		funcionarioAutenticado.setNome(nome);
		funcionarioAutenticado.setNif(nif);
		funcionarioAutenticado.setAtivo(true);
		funcionarioAutenticadoRepository.save(funcionarioAutenticado);
	}
}
