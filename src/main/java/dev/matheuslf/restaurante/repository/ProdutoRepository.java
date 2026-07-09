package dev.matheuslf.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.matheuslf.restaurante.domain.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
