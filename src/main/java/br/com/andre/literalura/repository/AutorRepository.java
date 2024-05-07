package br.com.andre.literalura.repository;

import br.com.andre.literalura.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByAutor(String autor);
}
