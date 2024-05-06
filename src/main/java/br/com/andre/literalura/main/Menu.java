package br.com.andre.literalura.main;

public class Menu {

    public static void menuPrincipal() {
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
