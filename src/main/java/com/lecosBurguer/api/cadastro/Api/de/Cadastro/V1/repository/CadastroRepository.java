package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.repository;

import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.entities.LcCadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastroRepository extends JpaRepository<LcCadastro, Long> {
    boolean existsByCpfCnpj(String cpfCnpj);

    boolean existsByEmail(String email);

    LcCadastro findBynome(String nome);

    LcCadastro findByEmail(String email);

}
