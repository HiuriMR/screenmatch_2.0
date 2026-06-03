package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

//preciso informar quem é a entidade e depois o tipo do id
public interface SerieRepository extends JpaRepository<Serie, Long> {
}
