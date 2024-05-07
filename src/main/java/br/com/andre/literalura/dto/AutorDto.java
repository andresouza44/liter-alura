package br.com.andre.literalura.dto;

public class AutorDto {

    String name;
    Integer birth_year;
    Integer death_year;

    public AutorDto (){

    }

    public AutorDto(String name, Integer birth_year, Integer death_year) {
        this.name = name;
        this.birth_year = birth_year;
        this.death_year = death_year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    @Override
    public String toString() {
        return "Autor: " + name +
                " \nbirth_year " + birth_year +
                " \ndeath_year" + death_year ;
    }
}
