package fag.edu.com.eduardozils.Models;

import java.util.Date;

public class Atendimento {
    private int codigo;
    private String assunto;
    private String contato;
    private String telefone;
    private String email;
    private String TipoAtendimento;
    private String data;
    private String empresa;

    public Atendimento(int codigo, String assunto, String contato, String telefone, String email, String tipoAtendimento, String data, String empresa) {
        this.codigo = codigo;
        this.assunto = assunto;
        this.contato = contato;
        this.telefone = telefone;
        this.email = email;
        TipoAtendimento = tipoAtendimento;
        this.data = data;
        this.empresa = empresa;
    }

    public Atendimento() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoAtendimento() {
        return TipoAtendimento;
    }

    public void setTipoAtendimento(String tipoAtendimento) {
        TipoAtendimento = tipoAtendimento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return  TipoAtendimento + " - " + assunto;
    }
}
