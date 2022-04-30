package com.example.agendamobileac2.ui.activities;

import static com.example.agendamobileac2.ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agendamobileac2.dao.PersonagemDAO;
import com.example.agendamobileac2.model.Personagem;
import com.example.agendamobileac2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//Classe utilizada para formar os dados do xml de Lista de Personagens inseridos
public class ListaPersonagemActivity extends AppCompatActivity {
    public static final String TITULO_APPBAR = "Lista de Personagens";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;

    //chamado na criacao da tela, define titulo, xml a ser invocado e metodos para inicializacao
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        setTitle(TITULO_APPBAR);
        configuraFabNovoPersonagem();
        configuraLista();
    }

    //Configura para ouvir o clique do botao de add personagem
    private void configuraFabNovoPersonagem() {
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_add);
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener()
        {
            //ao clicar chama o metodo para carregar o script de formulario
            @Override
            public void onClick(View view) { abreFormulario(); }
        });
    }

    //inicia o script de formulario
    private void abreFormulario() {
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }

    //chama o metodo de atualizar a lista ao recarregar/voltar a lista
    @Override
    protected void onResume() {
        super.onResume();
        atualizaPersonagem();
    }

    //atualiza a lista de personagens zerando ela e depois adicionando todos os personagens registrados
    private void atualizaPersonagem() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    //exclui o personagem que foi inserido no metodo
    private void remove(Personagem personagem){
        dao.remove(personagem);
        adapter.remove(personagem);
    }

    //cria a visualizacao do xml de lista de personagens
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_personagem_menu, menu);
    }

    //cria o pop-up de exclusao de personagem a partir do de seu xml
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_formulario_personagem_menu_remover){
            new AlertDialog.Builder(this)
                    .setTitle("Removendo Personagem")
                    .setMessage("Tem certeza que quer remover?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        //chama o metodo de remocao do personagem baseado no personagem excluido apos usuario aceitar a exclusao do item
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }

        return super.onContextItemSelected(item);
    }

    //Chama os devidos metodos para configuracao de visualizacao e clique da lista de personagens
    private void configuraLista(){
        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);
        configuraAdapter(listaDePersonagens);
        configuraItemPorClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }

    //Configura para ouvir o clique nos personagens (itens) da lista de personagens visualizada
    private void configuraItemPorClique(ListView listaDePersonagens) {
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //chama o metodo de editar personagem ao ter o item personagem clicado, utilizando a posicao do clique para receber o personagem
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
                abreFormularioEditar(personagemEscolhido);
            }
        });
    }

    //recebe um personagem a ser editado e abre o formulario de edicao
    private void abreFormularioEditar(Personagem personagemEscolhido) {
        Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido);
        startActivity(vaiParaFormulario);
    }

    //metodo para configurar a visualizacao da lista de personagens no ListView
    private void configuraAdapter(ListView listaDePersonagens){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter);
    }
}