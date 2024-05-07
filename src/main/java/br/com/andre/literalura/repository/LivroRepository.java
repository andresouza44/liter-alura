package br.com.andre.literalura.repository;

import br.com.andre.literalura.entity.Idioma;
import br.com.andre.literalura.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTituloContainingIgnoreCase(String nomeLivro);

    List<Livro> findByIdioma(Idioma idioma);

    @Query(value = """
            SELECT l FROM Livro l
            ORDER BY l.numeroDownloads DESC
            LIMIT 10
            """)
    List<Livro> top10LivrosBaixados();
}
