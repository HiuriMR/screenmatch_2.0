package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//anotação para informar que é um rest controller de Serie
@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieServico;


    //o que deve acontecer quando tiver um requisição do tipo GET no /series
    //metodo para aparecer todas as series na tela
    @GetMapping
    public List<SerieDTO> obterSeries() {
        return serieServico.obterTodasAsSeries();
    }
    //metodo para aparecer o top 5 de series na tela
    @GetMapping("/top5")
    public List<SerieDTO> obterSeriesTop5() {
        return serieServico.obterTop5Series();
    }
    //metodo para aparecer os lancamentos na tela
    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return serieServico.obterSeriesLancamentos();
    }
    //metodo para tratar a série quando selecionada
    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id){
        return serieServico.obterPorId(id);
    }
    //metodo para tratar as temporadas quando entra na Série
    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterEpisodiosPorId(@PathVariable Long id){
        return serieServico.obterTodasTemporadas(id);
    }

    //metodo para tratar as temporadas quando clicar na temporada
    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numero){
        return serieServico.obterTemporadasPorNumero(id, numero);
    }

    @GetMapping("/categoria/{categoria}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String categoria){
        return serieServico.obteSeriesPorCategoria(categoria);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obterTopEpisodiosTopTemporada(@PathVariable Long id){
        return serieServico.obterTopEpisodiosTopTemporada(id);
    }


}
