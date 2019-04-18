package fag.edu.com.eduardozils;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    private ListView lvEmpresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvEmpresa = findViewById(R.id.lvEmpresa);
        //Carregar a Lista

        final ArrayAdapter<String> adapterEmpresa = new ArrayAdapter<String>(ScrollingActivity.this, //Contexto
                R.layout.item, //Layout
                MainActivity.empresaList); // Lista de Objetos


        lvEmpresa.setAdapter(adapterEmpresa);

        lvEmpresa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String empresaSelecionada = adapterEmpresa.getItem(position);
                Intent output = new Intent(ScrollingActivity.this, MainActivity.class);
                output.putExtra("EMPRESASEL", empresaSelecionada);
                setResult(RESULT_OK, output);
                MainActivity.etEmpresa.setText(empresaSelecionada);
                finish();
            }
        });
    }
}
