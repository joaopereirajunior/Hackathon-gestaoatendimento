package br.com.fiap.gestaoatendimento.model;

public class UsuarioModel {
    private String username;
    private String password;

    public UsuarioModel(){}

    public UsuarioModel(String _username, String _password)
    {
        this.password = _password;
        this.username = _username;
    }

    // Getter para username
    public String getUsername() {
        return username;
    }

    // Setter para username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter para password
    public String getPassword() {
        return password;
    }

    // Setter para password
    public void setPassword(String password) {
        this.password = password;
    }
}