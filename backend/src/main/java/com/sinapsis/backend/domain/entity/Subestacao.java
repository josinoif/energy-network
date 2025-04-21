package com.sinapsis.backend.domain.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "TB_SUBESTACAO")
public class Subestacao {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUBESTACAO")
    private Long id;

    @Column(name = "CODIGO", length = 3, nullable = false, unique = true)
    private String codigo;

    @Column(name = "NOME", length = 100)
    private String nome;

    @Column(name = "LATITUDE", precision = 15, scale = 13, nullable = false)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", precision = 15, scale = 13)
    private BigDecimal longitude;

    @OneToMany(mappedBy = "subestacao", cascade = jakarta.persistence.CascadeType.ALL)
    private Set<RedeMT> redes;

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Subestacao)) {
            return false;
        }
        
        return new EqualsBuilder()
            .append(this.codigo, ((Subestacao) obj).codigo)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(this.codigo)
            .toHashCode();
    }

}
