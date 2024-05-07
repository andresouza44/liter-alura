package br.com.andre.literalura.main;


import br.com.andre.literalura.entity.Livro;
import br.com.andre.literalura.repository.AutorRepository;
import br.com.andre.literalura.repository.LivroRepository;
import br.com.andre.literalura.util.ConsumoApi;
import br.com.andre.literalura.util.SalvarDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;


public class Menu {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    Scanner scan = new Scanner(System.in);

    public Menu() {

    }

    public Menu(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void menu() {
        boolean sair = false;
        int opcao = -1;

        while (!sair) {
            imprimeMenuPrincipal();
            try {
                opcao = scan.nextInt();
                scan.nextLine();
                switch (opcao) {
                    case 1:
                        buscaDados();
                        break;
                    case 0:
                        sair = true;
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Entrada Inválida. Digite Novamente uma opção válida");
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada Inválida. Digite Novamente uma opção válida");
                scan.nextLine();

            }

        }


    }

    private void buscaDados() {
        ConsumoApi consumoApi = new ConsumoApi();
        SalvarDados salvarDados = new SalvarDados(livroRepository, autorRepository);

        System.out.println("Digite o nome do livro para busca");
        var nomeLivro = scan.nextLine();


        Optional<Livro> livro = livroRepository.findByTituloContainingIgnoreCase(nomeLivro);
        if (!livro.isPresent()) {
            System.out.println("Buscando .....");
            var url = "https://gutendex.com/books/?search=" + nomeLivro.replace(" ", "+");
            String json = consumoApi.obterDados(url);

            salvarDados.converterDados(json);
        } else {
            System.out.println("\n\nLivro já existe no cadastro");
        }
    }
        public static void imprimeMenuPrincipal () {
            String menuPrincipal = """
                                   
                    *******************************************
                              BEM VINDO AO ALURA LITER
                    *******************************************
                                   
                    Escolha uma das opções abaixo:
                                    
                    1 - Buscar livro por título
                    2 - Listar livros cadastrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                                    
                    0 - Sair
                                    
                    """;

            System.out.println(menuPrincipal);
        }


    }
