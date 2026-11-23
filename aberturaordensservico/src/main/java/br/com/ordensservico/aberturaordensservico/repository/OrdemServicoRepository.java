package br.com.ordensservico.aberturaordensservico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ordensservico.aberturaordensservico.model.OrdemServico;
import java.util.List;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer> {
    
    List<OrdemServico> findByDataEncerramentoIsNull();
}
