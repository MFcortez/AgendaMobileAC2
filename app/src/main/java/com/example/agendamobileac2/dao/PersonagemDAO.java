package com.example.agendamobileac2.dao;

import com.example.agendamobileac2.model.Personagem;

import java.util.List;
import java.util.ArrayList;

//faz o intermedio das informacoes da classe personagem com a visulizacao dele nas telas
public class PersonagemDAO {

    //a var personagens eh utilizada para guardar as entradas de dados do app e o int para manter a informacao do numero de entradas dentro da lista
    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeIds = 1;

    //Adiciona um novo personagem na lista
    public void salva(Personagem personagemSalvo) {
        personagemSalvo.setId(contadorDeIds);
        personagens.add(personagemSalvo);
        atualizaId();
    }

    //Atualiza o id (+1)
    private void atualizaId() { contadorDeIds++;}

    //Chama o personagem selecionado de volta para o formulario de edicao
    public void edita(Personagem personagem){
        Personagem personagemEncontrado = buscaPersonagemId(personagem);
        if (personagemEncontrado != null){
            int posicaoDoPersonagem = personagens.indexOf(personagemEncontrado);
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }

    //procura dentro da lista personagens a entrada personagem baseado no ID do objeto
    private Personagem buscaPersonagemId(Personagem personagem) {
        for (Personagem p : personagens) {
            if (p.getId() == personagem.getId()) {
                return p;
            }
        }
        return null;
    }

    //Retorna todas as entradas da lista personagens
    public List<Personagem> todos() { return new ArrayList<>(personagens); }

    //Remove o pesonagem solicitado (entrada)
    public void remove(Personagem personagem) {
        Personagem personagemDevolvido = buscaPersonagemId(personagem);
        if (personagemDevolvido != null) {
            personagens.remove(personagemDevolvido);
        }
    }
}
