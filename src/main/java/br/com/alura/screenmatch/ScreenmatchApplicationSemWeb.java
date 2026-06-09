//package br.com.alura.screenmatch;
//
//import br.com.alura.screenmatch.principal.Principal;
//import br.com.alura.screenmatch.repository.EpisodioRepository;
//import br.com.alura.screenmatch.repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchApplicationSemWeb implements CommandLineRunner {
//	//Necessário usar essa anotação porque a SerieRepository é uma interface e não pode ser istanciada, também para ttrabalhar com
//	//injeção de dependência a classe deve ser gerida pelo spring e a principal não é gerida pela spring
//	@Autowired
//	private SerieRepository serieRepository;
//	@Autowired
//	private EpisodioRepository episodioRepository;
//
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenmatchApplicationSemWeb.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		Principal principal = new Principal(serieRepository, episodioRepository);
//		principal.exibeMenu();
//	}
//}
