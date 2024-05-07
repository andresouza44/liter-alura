package br.com.andre.literalura.main;


import br.com.andre.literalura.entity.Autor;
import br.com.andre.literalura.entity.Idioma;
import br.com.andre.literalura.entity.Livro;
import br.com.andre.literalura.repository.AutorRepository;
import br.com.andre.literalura.repository.LivroRepository;
import br.com.andre.literalura.util.ConsumoApi;
import br.com.andre.literalura.util.SalvarDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.*;


public class Principal {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    Scanner scan = new Scanner(System.in);
    DoubleSummaryStatistics ds = new DoubleSummaryStatistics();

    public Principal() {

    }

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
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
                    case 2:
                        listarLivros();
                        break;

                    case 3:
                        listarAutores();
                        break;
                    case 4:
                        buscarAutorVivoPorData();
                        break;
                    case 5:
                        listarLivrosPorIdioma();
                        break;
                    case 6:
                        top10LivrosBaixados();
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
            System.out.println("\u001b[31m \u001b[3mBuscando .....\u001b[0m");
            var url = "https://gutendex.com/books/?search=" + nomeLivro.replace(" ", "+");
            String json = consumoApi.obterDados(url);

            salvarDados.converterDados(json);
        } else {
            System.out.println("\n\nLivro já existe no cadastro");
        }
    }

    public static void imprimeMenuPrincipal() {
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
                6 - Top 10 livros mais baixados
                                
                0 - Sair
                                
                """;

        System.out.println(menuPrincipal);
    }

    private void listarLivros() {
        List<Livro> livros = livroRepository.findAll(Sort.by("titulo"));
        imprimeLivros(livros);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll(Sort.by("autor"));
        imprimeAutores(autores);
    }

    private void buscarAutorVivoPorData() {
        System.out.println("Digite a data que deseja pesquisar");
        var data = scan.nextInt();
        scan.nextLine();
        List<Autor> autores = autorRepository.findByAutorVivoPorData(data);

        if (autores.isEmpty()) {
            System.out.println("Não foi encontrado nenhum escritor vivo no ano de " + data);
        } else {
            imprimeAutores(autores);

        }
    }

    public void listarLivrosPorIdioma() {

        System.out.println("""
                Insira o idioma que deseja realizar a busca
                es - espanhol
                en - inglês
                fr - francês
                pt = português
                """);
        var idioma = scan.nextLine().toUpperCase();
        try {
            Idioma idiomaEscolhido = Idioma.valueOf(idioma);
            List<Livro> livros = livroRepository.findByIdioma(idiomaEscolhido);
            if (livros.isEmpty()){
                System.out.println("\nNão foi encontrado nenhum livro em " + idiomaEscolhido.getNome());
            }else {
                imprimeLivros(livros);
                System.out.println("Foram encontrados " + livros.size() + " livros no idioma " +  idiomaEscolhido.getNome());
            }
        }
        catch (IllegalArgumentException e ){
            System.out.println("\nEntre com um idioma válido!");
            listarLivrosPorIdioma();
        }


    }
    private void top10LivrosBaixados(){
        System.out.println("Lista dos 10 Livros mais baixados");
        List<Livro> livros = livroRepository.top10LivrosBaixados();
        imprimeLivros(livros);

    }


    private void imprimeAutores(List<Autor> autores) {
        autores.forEach(a -> {
            System.out.println();
            System.out.print(
                    "\nAutor: " + a.getAutor() +
                            "\nAno de Nascimento: " + a.getAnoNascimento() +
                            "\nAno de Falecimento: " + a.getAnoFalecimento() +
                            "\nLivros :" + a.getLivros());
        });
    }

    private void imprimeLivros(List<Livro> livros) {
        livros.forEach(l -> {
            System.out.println(
                    "\n----- Livro -----" +
                            "\nTítulo: " + l.getTitulo() +
                            "\nAutor: " + l.getAutor().getAutor() +
                            "\nIdioma: " + l.getIdioma() +
                            "\nNúmero de downloads: " + l.getNumeroDownloads());
        });
    }


}
