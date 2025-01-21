package com.lecosBurguer.apis.repository;

import com.lecosBurguer.apis.entities.LcCadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastroRepository extends JpaRepository<LcCadastro, Long> {
    boolean existsByCpfCnpj(String cpfCnpj);

    boolean existsByEmail(String email);

    LcCadastro findByClient(String nome);

    LcCadastro findByEmail(String email);

    boolean existsByClient(String client);

}
