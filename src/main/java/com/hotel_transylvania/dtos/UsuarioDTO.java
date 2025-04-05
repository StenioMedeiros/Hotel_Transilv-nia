package com.hotel_transylvania.dtos;

import com.hotel_transylvania.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class UsuarioDTO {
    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "CPF não pode estar em branco")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "Telefone não pode estar em branco")
    @Pattern(regexp = "^\\(?(\\d{2})\\)?[\\s-]?\\d{4,5}[\\s-]?\\d{4}$", 
             message = "Telefone inválido. Use o formato (XX) XXXXX-XXXX ou (XX) XXXX-XXXX")
    private String telefone;

    @NotBlank(message = "Email não pode estar em branco")
    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "Tipo de usuário não pode ser nulo")
    private TipoUsuario tipoUsuario;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    
}