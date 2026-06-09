package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.EpisodioRepository;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("OMDB_APIKEY"); //"&apikey=f6ee7912";
    private List<DadosSerie> dadosSeries = new ArrayList<>();
    private Optional<Serie> serieBuscada;
    //Injeção de dependência com Spring
    private SerieRepository serieRepository;
    private EpisodioRepository episodioRepository;
    public Principal(SerieRepository serieRepository, EpisodioRepository episodioRepository) {
        this.serieRepository = serieRepository;
        this.episodioRepository = episodioRepository;

    }

    private List<Serie> series = new ArrayList<>();

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {

            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listas Séries Buscadas
                    4 - Buscar Série por Título
                    5 - Buscar Série por Ator
                    6 - Top 5 Séries
                    7 - Buscar Séries por Gênero
                    8 - Buscar Séries por Temporadas/Avaliação
                    9 - Buscar Episódio por trecho
                    10 - Top 5 Episódios por Série buscada
                    11 - Buscar episódios a partir de uma data
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarTopSeries();
                    break;
                case 7:
                    buscarSeriePorGenero();
                    break;
                case 8:
                    buscarSeriePorTemporadaAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    topEpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodiosDepoisDeUmaData();
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        //DadosSerie dadosSerie = getDadosSerie();
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome:");
        var nomeSerie = leitura.nextLine();
        //buscar o episódio a partir do nome da serie que vier
        Optional<Serie> serie = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);
                //series.stream()
                //.filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                //.findFirst(); // retorna a primeira referência que encontrar dessa série

        if(serie.isPresent()) {

            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream() // transformar um alista numa única sequência de elementos
                            .map(e -> new Episodio(d.numero(), e))) // transformar os dados
                    .collect(Collectors.toList()); // transformar numa nova lista

            serieEncontrada.setEpisodios(episodios);
            serieRepository.save(serieEncontrada);
        }else{
            System.out.println("Série não encontrada");
        }
    }

    private void listarSeriesBuscadas(){
        series = new ArrayList<>();
//        series = dadosSeries.stream()
//                .map(d -> new Serie(d))
//                .collect(Collectors.toList());
        series = serieRepository.findAll(); // a busca é feita no repositorio criado com o jpa
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        serieRepository.save(serie); //para salvar no banco de dados as series que forem buscadas
        System.out.println(dados);
    }

    private void buscarSeriePorTitulo() {

        System.out.println("Escolha uma série pelo nome:");
        var nomeSerie = leitura.nextLine();

        serieBuscada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);

        if(serieBuscada.isPresent()){
            System.out.println("Dados da Série: " + serieBuscada.get());
        }else{
            System.out.println("Série não encontrada");
        }
    }

    private void buscarSeriePorAtor() {
        System.out.println("Qual o nome do Ator/Atriz? ");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliações a partir de qual valor? ");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = serieRepository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Série em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }

    private void buscarTopSeries(){
        List<Serie> topSeries = serieRepository.findTop5ByOrderByAvaliacaoDesc();
        topSeries.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriePorGenero(){

        System.out.println("Digite o Gênero/Categoria: ");
        var genero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(genero); // tentar transformar em um Enum a categoria digitada
        List<Serie> listaGeneros = serieRepository.findByGenero(categoria);
        listaGeneros.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao() + " gênero: " + s.getGenero()));

    }

    private void buscarSeriePorTemporadaAvaliacao(){
        System.out.println("A série deve ter até quantas temporadas? ");
        var temporadas = leitura.nextInt();
        System.out.println("Deve ser maior de qual nota? ");
        var nota = leitura.nextDouble();

        //List<Serie> listaTemporadaAvaliacao = repository.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(temporadas, nota);
        List<Serie> listaTemporadaAvaliacao = serieRepository.seriesPorTemporadaAvaliacao(temporadas, nota);
        listaTemporadaAvaliacao.forEach(s ->
                System.out.println(s.getTitulo() + " total de temporadas " + s.getTotalTemporadas() +
                        " avaliação " + s.getAvaliacao()));

    }

    private void buscarEpisodioPorTrecho(){
        System.out.println("Digite o nome do episódio para busca: ");
        var trechoEpisodio = leitura.nextLine();

        //List<Episodio> epidosiosLista = episodioRepository.findByTituloContainingIgnoreCase(trechoEpisodio);
        List<Episodio> epidosiosLista = serieRepository.episodioPorTrecho(trechoEpisodio);
        epidosiosLista.forEach(e ->
                System.out.printf("Série: %s Temporada: %s - Episódio %s - %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(),
                        e.getNumeroEpisodio(), e.getTitulo()));

    }

    private void topEpisodiosPorSerie(){
        buscarSeriePorTitulo();
        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = serieRepository.topEpisodiosPorSerie(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Série: %s Temporada: %s - Episódio %s - %s Avaliação %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
        }

    }

    private void buscarEpisodiosDepoisDeUmaData(){
        buscarSeriePorTitulo();
        if (serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            System.out.println("Digite o ano limite de lançamento:");
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();

            List<Episodio> episodiosAno = serieRepository.episodioPorSerieEAno(serie, anoLancamento);
            episodiosAno.forEach(e ->
                    System.out.printf("Série: %s Temporada: %s - Episódio %s - %s -  Ano %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo(), e.getDataLancamento()));
        }
    }
}