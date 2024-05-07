package br.com.andre.literalura.entity;

public enum Idioma {
    ES("Espanhol"),
    EN("Inglês"),
    FR("Francês"),
    PT("Português");

    private String nome;

    public String getNome() {
        return nome;
    }

    Idioma(String nome) {
        this.nome = nome;
    }
}
