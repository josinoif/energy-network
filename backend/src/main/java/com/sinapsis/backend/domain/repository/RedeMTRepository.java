package com.sinapsis.backend.domain.repository;

import com.sinapsis.backend.domain.entity.RedeMT;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedeMTRepository extends JpaRepository<RedeMT, Long> {

    List<RedeMT> findAllBySubestacaoId(Long idSubestacao);

    Optional<RedeMT> findByIdAndSubestacaoId(Long idRede, Long idSubestacao);


}