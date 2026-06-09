package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodioRepository extends JpaRepository<Episodio, Long> {
    List<Episodio> findByTituloContainingIgnoreCase(String nome);
}
