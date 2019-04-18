package fag.edu.com.eduardozils.Util;

import android.app.AlertDialog;
import android.content.Context;

import fag.edu.com.eduardozils.R;

public class Mensagem {
    public static void ExibirMensagem(Context context, String msg, TipoMensagem tipo) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        if (tipo == TipoMensagem.ALERTA) {
            alert.setTitle("Atenção");
            alert.setIcon(R.drawable.ic_alerta);
        } else if (tipo == TipoMensagem.ERRO) {
            alert.setTitle("Erro");
            alert.setIcon(R.drawable.ic_erro);
        } else if (tipo == TipoMensagem.SUCESSO) {
            alert.setTitle("Sucesso");
            alert.setIcon(R.drawable.ic_done);
        }
        alert.setMessage(msg);
        alert.setNeutralButton("Ok", null);
        alert.show();

    }
}
