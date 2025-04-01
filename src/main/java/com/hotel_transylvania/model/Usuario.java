package com.hotel_transylvania.model;

import com.hotel_transylvania.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
	public Usuario(String nome, String cpf, String telefone, String email, String email2, TipoUsuario tipoUsuario) {
	    this.nome = nome;
	    this.cpf = cpf;
	    this.telefone = telefone;
	    this.email = email;
	    this.tipoUsuario = tipoUsuario;
	}


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private TipoUsuario tipoUsuario;
}
