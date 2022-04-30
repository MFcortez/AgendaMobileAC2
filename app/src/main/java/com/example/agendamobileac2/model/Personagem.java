package com.example.agendamobileac2.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

//Declara a classe personagem junto de suas variaveis
public class Personagem implements Serializable {
    private String nome;
    private String nascimento;
    private String altura;
    private int id = 0;

    //constroe a classe personagem tendo como entradas as variaveis declaradas acima
    public Personagem(String nome, String nascimento, String altura) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.altura = altura;
    }

    //Metodo para que o app faca algo quando personagem nao tiver uma entrada (futuramente aviso)
    public Personagem() {

    }

    //Definem o valor das variaveis do personagem
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }
    public void setAltura(String altura) {
        this.altura = altura;
    }

    //Pegam as variaveis do personagem para leitura
    public String getNome() {
        return nome;
    }
    public String getNascimento() {
        return nascimento;
    }
    public String getAltura() {
        return altura;
    }
    public int getId() { return id; }

    //Utilizado para retornar o nome do personagem quando a classe for chamada como string
    @NonNull
    @Override
    public String toString() {
        return nome;
    }

    //Chamado ao salvar o personagem para definir um id ah entrada
    public void setId(int id) {
        this.id = id;
    }
    
    public boolean IdValido() {
        return id > 0;
    }
}

