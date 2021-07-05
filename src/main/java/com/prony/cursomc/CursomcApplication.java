package com.prony.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.prony.cursomc.domain.Categoria;
import com.prony.cursomc.domain.Cidade;
import com.prony.cursomc.domain.Cliente;
import com.prony.cursomc.domain.Endereco;
import com.prony.cursomc.domain.Estado;
import com.prony.cursomc.domain.Pagamento;
import com.prony.cursomc.domain.PagamentoBoleto;
import com.prony.cursomc.domain.PagamentoCartao;
import com.prony.cursomc.domain.Pedido;
import com.prony.cursomc.domain.Produto;
import com.prony.cursomc.domain.enums.EstadoPagamento;
import com.prony.cursomc.domain.enums.TipoCliente;
import com.prony.cursomc.repositories.CategoriaRepository;
import com.prony.cursomc.repositories.CidadeRepository;
import com.prony.cursomc.repositories.ClienteRepository;
import com.prony.cursomc.repositories.EnderecoRepository;
import com.prony.cursomc.repositories.EstadoRepository;
import com.prony.cursomc.repositories.PagamentoRepository;
import com.prony.cursomc.repositories.PedidoRepository;
import com.prony.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepo;
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	@Autowired 
	private EstadoRepository estadoRepo;
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private PedidoRepository pedidoRepo;
	
	@Autowired
	private PagamentoRepository PagamentoRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepo.saveAll(Arrays.asList(cat1, cat2));
		produtoRepo.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlância", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		estadoRepo.saveAll(Arrays.asList(est1, est2));
		cidadeRepo.saveAll(Arrays.asList(cid1, cid2, cid3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678900", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("01139801234", "011998765432"));
		
		Endereco end1 = new Endereco(null, "Rua Flores", "300", "Apto 202", "Jardim Pirarara", "79864123", cli1, cid1);
		Endereco end2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "78123456", cli1, cid2);
		
		clienteRepo.save(cli1);
		enderecoRepo.saveAll(Arrays.asList(end1, end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("04/07/2021 21:30"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("05/07/2021 22:30"), cli1, end2);
		
		Pagamento pag1 = new PagamentoCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pag1);
		
		Pagamento pag2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("06/07/2021 08:00"), null);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
	}

}
