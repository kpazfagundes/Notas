package br.pucminas.notas.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.pucminas.notas.R;
import br.pucminas.notas.model.Nota;
import br.pucminas.notas.model.NotasDatabase;

public class AddActivity extends AppCompatActivity {

    private EditText edtTitulo;
    private EditText edtTexto;
    private NotasDatabase  db;
    private Nota notaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtTitulo = findViewById(R.id.edtTituloId);
        edtTexto = findViewById(R.id.edtTextoId);

        db = NotasDatabase.getDATABASE(AddActivity.this);

        // RECUPERAR A NOTA ENVIADA PELA ACTIVITY
        notaSelecionada = (Nota) getIntent().getSerializableExtra("nota");

        // CARREGA NA TELA AS INFORMAÇÕES DA  NOTA SELECIONADA
        if(notaSelecionada != null){
            edtTitulo.setText(notaSelecionada.getTitulo());
            edtTexto.setText(notaSelecionada.getTexto());
        }

    }

    Boolean validaCampo(String campo){
        if(campo == null || campo.equals(""))
            return false;
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.mnAddId){

            String titulo = edtTitulo.getText().toString();
            String texto = edtTexto.getText().toString();

            if(validaCampo(titulo) && validaCampo(titulo)){

                // INSERT
                if(notaSelecionada == null) {

                    Nota nota = new Nota();
                    nota.setTitulo(titulo);
                    nota.setTexto(texto);

                    db.notaDAO().insert(nota);

                    Toast.makeText(this,
                            "Nota inserida com sucesso!",
                            Toast.LENGTH_LONG).show();

                    finish();

                }else { // UPDATE

                    notaSelecionada.setTitulo(titulo);
                    notaSelecionada.setTexto(texto);
                    db.notaDAO().update(notaSelecionada);

                    Toast.makeText(this,
                            "Nota alterada com sucesso!",
                            Toast.LENGTH_LONG).show();

                    finish();

                }

            }else{
                Toast.makeText(this,
                        "Obrigatório informar os campos!",
                        Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
