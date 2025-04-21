package com.sinapsis.backend.domain.repository;

import com.sinapsis.backend.domain.entity.Subestacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubestacaoRepository extends JpaRepository<Subestacao, Long> {
}