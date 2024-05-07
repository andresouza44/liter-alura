package br.com.andre.literalura.util;

import br.com.andre.literalura.dto.AutorDto;
import br.com.andre.literalura.dto.LivroDto;
import br.com.andre.literalura.dto.ResultDto;
import br.com.andre.literalura.entity.Autor;
import br.com.andre.literalura.entity.Idioma;
import br.com.andre.literalura.entity.Livro;
import br.com.andre.literalura.repository.AutorRepository;
import br.com.andre.literalura.repository.LivroRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalvarDados {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    public SalvarDados() {
    }

    public SalvarDados(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    static Gson gson = new Gson();

    public void converterDados(String json) {

        ResultDto results = gson.fromJson(json, ResultDto.class);

        if (!results.results().isEmpty()) {

            LivroDto livroDto = results.results().get(0);
            AutorDto autorDto = livroDto.getAuthors().get(0);

            gravaDados(autorDto, livroDto);

        } else {
            System.out.println("Livro não encontrado! ");
        }
    }

    private void gravaDados(AutorDto autorDto, LivroDto livroDto) {
        Livro livro = new Livro();
        Optional<Autor> autorOptional = autorRepository.findByAutor(livroDto.getAuthors().get(0).getName());
        Autor autor = new Autor();
        if (autorOptional.isPresent()){
            autor = autorOptional.get();
        }else {
            autor.setAutor(autorDto.getName());
            autor.setAnoFalecimento(autorDto.getDeath_year());
            autor.setAnoNascimento(autorDto.getBirth_year());
            autorRepository.save(autor);
        }

        livro.setAutor(autor);
        livro.setTitulo(livroDto.getTitle());
        livro.setIdioma(Idioma.valueOf(livroDto.getLanguages().get(0).toUpperCase()));
        livro.setNumeroDownloads(livroDto.getDownload_count());

        livroRepository.save(livro);

        System.out.println(autor);
        System.out.println(livro);


    }

}

