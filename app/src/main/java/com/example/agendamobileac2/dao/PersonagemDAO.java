package com.example.agendamobileac2.dao;

import com.example.agendamobileac2.model.Personagem;

import java.util.List;
import java.util.ArrayList;


public class PersonagemDAO {

    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeIds = 1;

    public void salva(Personagem personagemSalvo) {
        personagemSalvo.setId(contadorDeIds);
        personagens.add(personagemSalvo);
        atualizaId();
    }
    private void atualizaId() { contadorDeIds++;}

    public void edita(Personagem personagem){
        Personagem personagemEncontrado = buscaPersonagemId(personagem);
        if (personagemEncontrado != null){
            int posicaoDoPersonagem = personagens.indexOf(personagemEncontrado);
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }

    private Personagem buscaPersonagemId(Personagem personagem) {
        for (Personagem p : personagens) {
            if (p.getId() == personagem.getId()) {
                return p;
            }
        }
        return null;
    }

    public List<Personagem> todos() { return new ArrayList<>(personagens); }

    public void remove(Personagem personagem) {
        Personagem personagemDevolvido = buscaPersonagemId(personagem);
        if (personagemDevolvido != null) {
            personagens.remove(personagemDevolvido);
        }
    }
}
