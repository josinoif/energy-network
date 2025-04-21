package com.sinapsis.backend.domain.entity;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "TB_REDE_MT")
public class RedeMT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REDE_MT")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUBESTACAO", nullable = false)
    private Subestacao subestacao;

    @Column(name = "CODIGO", length = 5, nullable = false, unique = true)
    private String codigo;

    @Column(name = "NOME", length = 100)
    private String nome;

    @Column(name = "TENSAO_NOMINAL", precision = 5, scale = 2)
    private BigDecimal tensaoNominal;

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RedeMT)) {
            return false;
        }
        
        return new EqualsBuilder()
            .append(this.codigo, ((RedeMT) obj).codigo)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(this.codigo)
            .toHashCode();
    }

    

}
