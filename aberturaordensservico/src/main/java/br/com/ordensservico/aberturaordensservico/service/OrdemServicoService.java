package br.com.ordensservico.aberturaordensservico.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.ordensservico.aberturaordensservico.dto.EncerramentoOrdemServicoRequest;
import br.com.ordensservico.aberturaordensservico.dto.OrdemServicoRequest;
import br.com.ordensservico.aberturaordensservico.model.Equipamento;
import br.com.ordensservico.aberturaordensservico.model.OrdemServico;
import br.com.ordensservico.aberturaordensservico.repository.EquipamentoRepository;
import br.com.ordensservico.aberturaordensservico.repository.OrdemServicoRepository;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;
    private final EquipamentoRepository equipamentoRepository;

    public OrdemServicoService(OrdemServicoRepository ordemServicoRepository,
                               EquipamentoRepository equipamentoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
        this.equipamentoRepository = equipamentoRepository;
    }

    public Optional<OrdemServico> cadastrar(OrdemServicoRequest dados) {
        Optional<Equipamento> equipamentoEncontrado = equipamentoRepository.findById(dados.getEquipamentoId());

        if (equipamentoEncontrado.isEmpty()) {
            return Optional.empty();
        }

        OrdemServico ordem = new OrdemServico();
        ordem.setDescricao(dados.getDescricao());
        ordem.setEquipamento(equipamentoEncontrado.get());
        ordem.setDataAbertura(LocalDateTime.now());

        return Optional.of(ordemServicoRepository.save(ordem));
    }

    public List<OrdemServico> listar() {
        return ordemServicoRepository.findAll();
    }

    public List<OrdemServico> listarAbertas() {
        return ordemServicoRepository.findByDataEncerramentoIsNull();
    }

    public Optional<OrdemServico> buscarPorId(Integer id) {
        return ordemServicoRepository.findById(id);
    }

    public Optional<OrdemServico> encerrar(Integer id, EncerramentoOrdemServicoRequest dados) {
        Optional<OrdemServico> ordemEncontrada = ordemServicoRepository.findById(id);

        if (ordemEncontrada.isEmpty()) {
            return Optional.empty();
        }

        OrdemServico ordem = ordemEncontrada.get();

        if (ordem.getDataEncerramento() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta ordem de serviço já foi encerrada"
            );
        }

        ordem.setSolucao(dados.getSolucao());
        ordem.setDataEncerramento(LocalDateTime.now());

        return Optional.of(ordemServicoRepository.save(ordem));
    }
}