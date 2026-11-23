package br.com.ordensservico.aberturaordensservico.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ordensservico.aberturaordensservico.dto.EncerramentoOrdemServicoRequest;
import br.com.ordensservico.aberturaordensservico.dto.OrdemServicoRequest;
import br.com.ordensservico.aberturaordensservico.model.OrdemServico;
import br.com.ordensservico.aberturaordensservico.service.OrdemServicoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @PostMapping
    public ResponseEntity<OrdemServico> cadastrar(
            @Valid @RequestBody OrdemServicoRequest dados) {

        Optional<OrdemServico> ordemCadastrada = ordemServicoService.cadastrar(dados);

        if (ordemCadastrada.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ordemCadastrada.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<OrdemServico>> listar() {
        List<OrdemServico> ordens = ordemServicoService.listar();
        return ResponseEntity.ok(ordens);
    }

    @GetMapping("/abertas")
    public ResponseEntity<List<OrdemServico>> listarAbertas() {
        List<OrdemServico> abertas = ordemServicoService.listarAbertas();
        return ResponseEntity.ok(abertas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServico> buscarPorId(@PathVariable Integer id) {
        Optional<OrdemServico> ordem = ordemServicoService.buscarPorId(id);

        if (ordem.isPresent()) {
            return ResponseEntity.ok(ordem.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/encerramento")
    public ResponseEntity<OrdemServico> encerrar(
            @PathVariable Integer id,
            @Valid @RequestBody EncerramentoOrdemServicoRequest dados) {

        Optional<OrdemServico> ordemEncerrada = ordemServicoService.encerrar(id, dados);

        if (ordemEncerrada.isPresent()) {
            return ResponseEntity.ok(ordemEncerrada.get());
        }

        return ResponseEntity.notFound().build();
    }
}