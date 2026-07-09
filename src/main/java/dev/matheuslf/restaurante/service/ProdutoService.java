package dev.matheuslf.restaurante.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.matheuslf.restaurante.domain.entity.CategoriaProduto;
import dev.matheuslf.restaurante.domain.entity.Produto;
import dev.matheuslf.restaurante.dto.ProdutoRequest;
import dev.matheuslf.restaurante.dto.ProdutoResponse;
import dev.matheuslf.restaurante.repository.CategoriaProdutoRepository;
import dev.matheuslf.restaurante.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final CategoriaProdutoRepository categoriaProdutoRepository;

    public ProdutoResponse cadastrar(ProdutoRequest request) {
        CategoriaProduto categoriaProduto = buscarCategoriaPorId(request.categoriaId());
        Produto produto = request.toEntity(categoriaProduto);
        return ProdutoResponse.fromEntity(produtoRepository.save(produto));
    }

    public Page<ProdutoResponse> listar(Pageable pageable) {
        return produtoRepository.findAll(pageable).map(ProdutoResponse::fromEntity);
    }

    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = buscarProdutoPorId(id);
        return ProdutoResponse.fromEntity(produto);
    }

    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        Produto produto = buscarProdutoPorId(id);
        CategoriaProduto categoriaProduto = buscarCategoriaPorId(request.categoriaId());
        request.preencher(produto, categoriaProduto);
        return ProdutoResponse.fromEntity(produtoRepository.save(produto));
    }

    public void excluir(Long id) {
        Produto produto = buscarProdutoPorId(id);
        produtoRepository.delete(produto);
    }

    private CategoriaProduto buscarCategoriaPorId(Long id) {
        return categoriaProdutoRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
    }

    private Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
    }
}
