package dev.matheuslf.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.matheuslf.restaurante.domain.entity.CategoriaProduto;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {

}
