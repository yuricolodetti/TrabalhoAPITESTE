package br.com.serratec.entity.pk;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class UsuarioPerfilPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer usuarioId; // armazenando apenas o ID
    private Long perfilId;     // armazenando apenas o ID

    public UsuarioPerfilPK() {}

    public UsuarioPerfilPK(Integer usuarioId, Long perfilId) {
        this.usuarioId = usuarioId;
        this.perfilId = perfilId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    // hashCode e equals são obrigatórios para PK composta
    @Override
    public int hashCode() {
        return usuarioId.hashCode() + perfilId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UsuarioPerfilPK)) return false;
        UsuarioPerfilPK other = (UsuarioPerfilPK) obj;
        return usuarioId.equals(other.usuarioId) && perfilId.equals(other.perfilId);
    }
}
