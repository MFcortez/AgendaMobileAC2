package com.example.agendamobileac2.ui.activities;

import static com.example.agendamobileac2.ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agendamobileac2.dao.PersonagemDAO;
import com.example.agendamobileac2.model.Personagem;
import com.example.agendamobileac2.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

//Classe utilizada para formar o formulario de edicao do xml de adicionar/editar personagem
public class FormularioPersonagemActivity extends AppCompatActivity {
    //define os dois possiveis titulos da exibicao
    private static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar o Personagem";
    private static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";
    //referencia os campos que um personagem contem
    private EditText campoNome;
    private EditText campoNascimento;
    private EditText campoAltura;
    //cria um dao para intermediar a visulizacao da classe personagem
    private final PersonagemDAO dao = new PersonagemDAO();
    private Personagem personagem;

    //cria a visualizacao do xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //define o botao salvar personagem para que chame o metodo finalizar form
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int itemId = item.getItemId();
        if (itemId == R.id.activity_formulario_personagem_menu_salvar) {
            finalizarFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    //chamado na criacao da tela, define xml a ser invocado e metodos para inicializacao
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        inicializacaoCampos();
        carregaPersonagem();
    }

    //chamado pelo onCreate para carregar as informacoes do personagem no formulario
    private void carregaPersonagem() {
        Intent dados = getIntent();
        //caso haja a entrada de um personagem, configura o form como edicao de personagem
        if (dados.hasExtra(CHAVE_PERSONAGEM)) {
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preenchaCampos();
        } else { //configura o form como adicao de personagem
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagem = new Personagem();
        }
    }

    //chamado caso seja edicao de personagem para adicionar as infos do personagem registrado nos campos do form
    private void preenchaCampos() {
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
    }

    //chamado ao apertar o botao de salvar
    private void finalizarFormulario()
    {
        preencherPersonagem();
        //caso o personagem tenha um id valido (diferente de 0), as infos dele sao editadas
        if (personagem.IdValido()) {
            dao.edita(personagem);
        } else { //salva como novo personagem
            dao.salva(personagem);
        }
        finish();
    }

    //seta os campos do xml formulario, inclusive formatacao e "deteccao" de edicao
    private void inicializacaoCampos() {
        campoNome = findViewById(R.id.editText_Nome);
        campoNascimento = findViewById(R.id.editText_Nascimento);
        campoAltura = findViewById(R.id.editText_Altura);

        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener(mtwAltura);

        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoNascimento.addTextChangedListener(mtwNascimento);
    }

    //Define as informacoes presentes no formulario como um novo personagem
    private void preencherPersonagem() {
        String nome = campoNome.getText().toString();
        String nascimento = campoNascimento.getText().toString();
        String altura = campoAltura.getText().toString();

        //faz a entrada das informacoes coletada acima na classe personagem
        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);

    }
}
