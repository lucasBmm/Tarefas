package br.com.tarefas.service;

import br.com.tarefas.entities.Departamento;
import br.com.tarefas.entities.Pessoa;
import br.com.tarefas.entities.Tarefa;
import br.com.tarefas.record.TarefaRecord;
import br.com.tarefas.repository.DepartamentoRepository;
import br.com.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository repository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Tarefa adicionarTarefa(TarefaRecord tarefaRecord) {
        Tarefa tarefa = new Tarefa(tarefaRecord);

        var departamento = departamentoRepository.getById(tarefaRecord.departamento().id());

        tarefa.setDepartamento(departamento);

        return repository.save(tarefa);
    }

    public Tarefa finalizaTarefa(Long id) {
        Optional<Tarefa> optionalTarefa = repository.findById(id);

        if (optionalTarefa.isPresent()) {
            Tarefa tarefa = optionalTarefa.get();
            if (!tarefa.isFinalizado()) {
                tarefa.setFinalizado(true);
                return repository.save(tarefa);
            }
        }

        throw new RuntimeException("Tarefa não encontrada ou já finalizada");
    }

    public List<Tarefa> listarTarefasPendentes(int maxTarefas) {
        return repository.findTarefasPendentesOrderByPrazo(maxTarefas);
    }

    public void alocarPessoaNaTarefa(Long tarefaId, Long pessoaId) {
        Optional<Tarefa> optionalTarefa = repository.findById(tarefaId);
        Optional<Pessoa> optionalPessoa = pessoaService.findById(pessoaId);

        if (optionalTarefa.isPresent() && optionalPessoa.isPresent()) {
            Tarefa tarefa = optionalTarefa.get();
            Pessoa pessoa = optionalPessoa.get();

            Departamento departamentoTarefa = tarefa.getDepartamento();
            Departamento departamentoPessoa = pessoa.getDepartamento();

            if (departamentoTarefa == null || departamentoPessoa == null || !departamentoTarefa.equals(departamentoPessoa)) {
                throw new RuntimeException("A pessoa é de departamento diferente!");
            }

            tarefa.setPessoa(pessoa);
            repository.save(tarefa);
        } else {
            throw new RuntimeException("Tarefa ou pessoa não encontrada!");
        }
    }
}
