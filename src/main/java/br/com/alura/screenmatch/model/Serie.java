package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.traducao.ConsultaMyMemory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity //indica que a classe será uma tabela no pgAdmin
@Table(name = "series") //indica que o nome da tabela será series
public class Serie {
    @Id //indica que a classe terá um id do tipo long
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indica que o tipo desse id será de responsabilidade do banco, geralmente auto-incremento.
    private Long id;
    @Column(unique = true) //indica que o titulo nunca vai se repetir
    private String titulo;
    private Integer totalTemporadas;
    private String data;
    private Double avaliacao;
    @Enumerated(EnumType.STRING) //indica que será gravado como string o Enum
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;
    //@Transient //indica que o atributo lista de séries vai ficar sem tratamento no banco, está de canto para ser tratado depois

    //utilizar o CascadeType.ALL para informar que tudo que for feito na série ser espelhado no episódio, ex: serie apagada episódios apagados
    //FetchType.EAGER é uma estratégia de carregamento que instrui o sistema de persistência (como o Hibernate) a buscar e carregar os dados de um relacionamento imediatamente junto com a entidade principal
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)  //um para muitos, precisa mapear na Classe Episódios para ter algo Bidirecionais
    private List<Episodio> episodios = new ArrayList<>();

    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.data = dadosSerie.data();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0.0);
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim()); // vai pegar apenas o primeiro gereno do json
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        //this.sinopse = ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim();
        this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()).trim();

    }

    protected Serie() {}

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public List<Episodio> getEpisodios(){
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios){
        episodios.forEach(e -> e.setSerie(this)); // Informa quem é a dona dos episódios. Para cada episódio encontrado ele seta essa série
        this.episodios = episodios;
    }

    public String getTitulo(){
        return titulo;
    }

    public Integer getTotalTemporadas(){
        return totalTemporadas;
    }

    public String getData(){
        return data;
    }

    public Double getAvaliacao(){
        return avaliacao;
    }

    public Categoria getGenero(){
        return genero;
    }

    public String getAtores(){
        return atores;
    }

    public String getPoster(){
        return poster;
    }

    public String getSinopse(){
        return sinopse;
    }

    @Override
    public String toString() {
        return
                "genero=" + genero +
                ", titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", data=" + data +
                ", avaliacao=" + avaliacao +
                ", atores='" + atores + '\'' +
                ", poster='" + poster + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", episodios='" + episodios + '\'';
    }
}
