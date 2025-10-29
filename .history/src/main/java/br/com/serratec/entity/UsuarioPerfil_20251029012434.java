package br.com.serratec.entity;

import java.time.LocalDate;
import br.com.serratec.entity.pk.UsuarioPerfilPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario_perfil")
public class UsuarioPerfil {

    @EmbeddedId
    private UsuarioPerfilPK id = new UsuarioPerfilPK();

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "id_usuario")
    private User usuario;

    @ManyToOne
    @MapsId("perfilId")
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    private LocalDate dataCriacao;

    public UsuarioPerfil() {}

    public UsuarioPerfil(User usuario, Perfil perfil, LocalDate dataCriacao) {
        this.usuario = usuario;
        this.perfil = perfil;
        this.dataCriacao = dataCriacao;
        this.id.setUsuarioId(usuario.getUserId());
        this.id.setPerfilId(perfil.getId());
    }

    public UsuarioPerfilPK getId() {
        return id;
    }

    public void setId(UsuarioPerfilPK id) {
        this.id = id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
        this.id.setUsuarioId(usuario.getUserId());
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
        this.id.setPerfilId(perfil.getId());
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
