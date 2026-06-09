package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> obterTodasAsSeries(){
        return converteDados(serieRepository.findAll());
    }


    public List<SerieDTO> obterTop5Series() {
        return converteDados(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> obterSeriesLancamentos() {
        return converteDados(serieRepository.lancamentosMaisRecentes());
    }

    private List<SerieDTO> converteDados(List<Serie> series){
        return series.stream()
                .map(serie -> new SerieDTO(
                        serie.getId(),
                        serie.getTitulo(),
                        serie.getTotalTemporadas(),
                        serie.getData(),
                        serie.getAvaliacao(),
                        serie.getGenero(),
                        serie.getAtores(),
                        serie.getPoster(),
                        serie.getSinopse()
                ))
                .toList();
    }


    public SerieDTO obterPorId(Long id) {

        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(),s.getData(),s.getAvaliacao(),
                    s.getGenero(),s.getAtores(),s.getPoster(),s.getSinopse());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(),
                            e.getTitulo()))
                    .collect(Collectors.toList());
        }
        return null;

    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        return serieRepository.obterEpisodiosPorTemporada(id, numero).stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(),
                        e.getTitulo()))
                .collect(Collectors.toList());

    }

    public List<SerieDTO> obteSeriesPorCategoria(String categoria) {
        Categoria nomeCategoria = Categoria.fromPortugues(categoria);
        return converteDados(serieRepository.findByGenero(nomeCategoria));
    }

    public List<EpisodioDTO> obterTopEpisodiosTopTemporada(Long id) {
        return serieRepository.obterTopEpisodiosTopTemporada(id).stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(),
                        e.getTitulo()))
                .collect(Collectors.toList());
    }
}
