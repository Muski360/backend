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


	//feito pelo gpt para dar seed inicial.
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
			salvarFuncionarioInicial(funcionarioRepository, "Administrador Senai", "1001", "1234");

			// cria categorias iniciais
			Categoria informatica = salvarCategoriaSeNaoExistir(categoriaRepository, "Informatica",
					"equipamentos e perifericos");
			Categoria laboratorio = salvarCategoriaSeNaoExistir(categoriaRepository, "Laboratorio",
					"materiais de uso tecnico");
			salvarCategoriaSeNaoExistir(categoriaRepository, "Limpeza", "itens de apoio e higienizacao");

			// cria materiais iniciais
			Material notebook = salvarMaterialSeNaoExistir(materialRepository, informatica, "Notebook",
					"notebook para uso em sala", "unidade", 3);
			Material multimetro = salvarMaterialSeNaoExistir(materialRepository, laboratorio, "Multimetro",
					"equipamento de medicao", "unidade", 2);

			// cria entradas iniciais no estoque
			if (movimentacaoEstoqueRepository.count() == 0) {
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

	// salva o usuario inicial se ele ainda nao existir
	private void salvarFuncionarioInicial(FuncionarioRepository funcionarioRepository, String nome, String nif,
			String senha) {
		if (funcionarioRepository.findByNifAndAtivoTrue(nif).isPresent()) {
			return;
		}

		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setNif(nif);
		funcionario.setSenha(senha);
		funcionario.setAtivo(true);
		funcionarioRepository.save(funcionario);
	}

	// salva a categoria inicial se ela ainda nao existir
	private Categoria salvarCategoriaSeNaoExistir(CategoriaRepository categoriaRepository, String nome,
			String descricao) {
		return categoriaRepository.findByNomeIgnoreCase(nome).orElseGet(() -> {
			Categoria categoria = new Categoria();
			categoria.setNome(nome);
			categoria.setDescricao(descricao);
			return categoriaRepository.save(categoria);
		});
	}

	// salva o material inicial se ele ainda nao existir
	private Material salvarMaterialSeNaoExistir(MaterialRepository materialRepository, Categoria categoria,
			String nome, String descricao, String unidadeMedida, Integer quantidadeMinima) {
		return materialRepository.findByNomeIgnoreCase(nome).orElseGet(() -> {
			Material material = new Material();
			material.setNome(nome);
			material.setDescricao(descricao);
			material.setUnidadeMedida(unidadeMedida);
			material.setQuantidadeMinima(quantidadeMinima);
			material.setQuantidadeEmEstoque(0);
			material.setCategoria(categoria);
			return materialRepository.save(material);
		});
	}
}
