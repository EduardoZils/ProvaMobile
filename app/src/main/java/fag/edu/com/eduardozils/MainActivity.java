package fag.edu.com.eduardozils;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fag.edu.com.eduardozils.Models.Atendimento;
import fag.edu.com.eduardozils.Util.Mensagem;
import fag.edu.com.eduardozils.Util.TipoMensagem;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private int day, mounth, year;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private boolean isEdiacao = false;
    private Atendimento atendimento = null;
    private int indexEdicao = 0;

    Button btSalvar, btCancelar;
    ImageButton ibPesquisar;
    ListView lvAtendimento;
    Spinner spTipoAtendimento;
    EditText etAssunto, etContato, etTelefone, etEmail, etData;
    public static EditText etEmpresa;
    List<Atendimento> atendimentoList = new ArrayList<Atendimento>();
    public static List<String> empresaList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carregaXML();
        carregaDados();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void carregaDados() {
        year = calendar.get(Calendar.YEAR);
        mounth = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(MainActivity.this, this, year, mounth, day);

        adicionaSpinner();

        etData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mensagem.ExibirMensagem(MainActivity.this, "Atendimento Cancelado", TipoMensagem.ALERTA);
                if(isEdiacao) {

                    isEdiacao = false;

                    btSalvar.setText(R.string.salvar);
                }
                limpacampos();
                carregarLista();
            }
        });

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etAssunto.getText().toString().length() > 0 && etContato.getText().toString().length() > 0 && etData.getText().toString().length() > 0 && etEmail.getText().toString().length() > 0 &&
                        etTelefone.getText().toString().length() > 0 && etEmpresa.getText().toString().length() > 0) {
                    if (!isEdiacao) {

                        atendimento = new Atendimento();
                        atendimento.setAssunto(etAssunto.getText().toString());
                        atendimento.setCodigo(atendimentoList.size() + 1);
                        atendimento.setContato(etContato.getText().toString());
                        atendimento.setData(etData.getText().toString());
                        atendimento.setEmail(etEmail.getText().toString());
                        atendimento.setTelefone(etTelefone.getText().toString());
                        atendimento.setTipoAtendimento(spTipoAtendimento.getSelectedItem().toString());
                        atendimento.setEmpresa(etEmpresa.getText().toString());

                    }

                    if(isEdiacao){
                        atendimentoList.set(indexEdicao , atendimento);
                        btSalvar.setText(R.string.salvar);

                    }else{
                        atendimentoList.add(atendimento);
                    }

                    if(isEdiacao){
                        Mensagem.ExibirMensagem(MainActivity.this, "Atendimento Atualizado com Sucesso", TipoMensagem.SUCESSO);
                    }else {
                        Mensagem.ExibirMensagem(MainActivity.this, "Atendimento Registrado com Sucesso", TipoMensagem.SUCESSO);
                    }
                    isEdiacao = false;
                } else {
                    Mensagem.ExibirMensagem(MainActivity.this, "Existem campos que não foram preenchidos", TipoMensagem.ERRO);
                }
                carregarLista();
                limpacampos();
            }
        });

        ibPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
                startActivity(intent);
            }
        });

        lvAtendimento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                atendimento = (Atendimento) lvAtendimento.getItemAtPosition(position);
                isEdiacao = true;
                indexEdicao = position;
                btSalvar.setText(R.string.atualizar);
                carregaCampos();
            }

            private void carregaCampos() {
                etAssunto.setText(atendimento.getAssunto());
                etContato.setText(atendimento.getContato());
                etTelefone.setText(atendimento.getTelefone());
                etEmail.setText(atendimento.getEmail());
                etData.setText(atendimento.getData());
                etEmpresa.setText(atendimento.getEmpresa());
            }
        });

        lvAtendimento.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final AlertDialog.Builder alertConfirmacao = new AlertDialog.Builder(MainActivity.this);
                alertConfirmacao.setTitle("Confirmação de Exclusão");
                alertConfirmacao.setMessage("Deseja realmente excluir o registro?");
                alertConfirmacao.setIcon(R.drawable.ic_alerta);
                alertConfirmacao.setNeutralButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        atendimentoList.remove(position);
                        carregarLista();
                    }
                });
                alertConfirmacao.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertConfirmacao.show();

                limpacampos();
                return true;
            }
        });
    }

    private void adicionaSpinner() {
        List<String> empresas = new ArrayList<String>();
        empresas.add("Melhoria");
        empresas.add("Erro");
        empresas.add("Manutenção");
        ArrayAdapter<String> adapterEmpresa = new ArrayAdapter<String>(MainActivity.this, //Contexto
                R.layout.item, //Layout
                empresas); // Lista de Objetos
        spTipoAtendimento.setAdapter(adapterEmpresa);

        empresaList.add("Maxicon");
        empresaList.add("JunSoft");
        empresaList.add("Inside");
        empresaList.add("FAG");

    }

    private void limpacampos() {
        etTelefone.setText("");
        etEmail.setText("");
        etContato.setText("");
        etAssunto.setText("");
        etData.setText("");
        etEmpresa.setText("");
        carregarLista();
    }

    private void carregaXML() {
        btCancelar = findViewById(R.id.btCancelar);
        btSalvar = findViewById(R.id.btSalvar);
        ibPesquisar = findViewById(R.id.ibPesquisar);
        spTipoAtendimento = findViewById(R.id.spTipoAtendimento);
        etEmpresa = findViewById(R.id.etEmpresa);
        etData = findViewById(R.id.etData);
        etAssunto = findViewById(R.id.etAssunto);
        etContato = findViewById(R.id.etContato);
        etEmail = findViewById(R.id.etEmail);
        etTelefone = findViewById(R.id.etTelefone);
        lvAtendimento = findViewById(R.id.lvAtendimento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        etData.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
    }

    private void carregarLista() {
        ArrayAdapter<Atendimento> adapterAtendimento = new ArrayAdapter<Atendimento>(MainActivity.this, //Contexto
                R.layout.item, //Layout
                atendimentoList); // Lista de Objetos
        lvAtendimento.setAdapter(adapterAtendimento);
        if (lvAtendimento.getCount() > 0) {
            calculeHeightListView();
        }
    }

    private void calculeHeightListView() {
        int totalHeight = 0;
        ListAdapter adapter = lvAtendimento.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, lvAtendimento);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lvAtendimento.getLayoutParams();
        params.height = totalHeight + (lvAtendimento.getDividerHeight() * (adapter.getCount()) + 10);
        lvAtendimento.setLayoutParams(params);
        lvAtendimento.requestLayout();
    }


}
