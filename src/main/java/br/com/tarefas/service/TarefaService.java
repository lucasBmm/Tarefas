package br.com.tarefas.service;

import br.com.tarefas.entities.Tarefa;
import br.com.tarefas.record.TarefaRecord;
import br.com.tarefas.repository.DepartamentoRepository;
import br.com.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository repository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Tarefa adicionarTarefa(TarefaRecord tarefaRecord) {
        Tarefa tarefa = new Tarefa(tarefaRecord);

        var departamento = departamentoRepository.getById(tarefaRecord.departamento().id());

        tarefa.setDepartamento(departamento);

        return repository.save(tarefa);
    }

    public Tarefa finalizaTarefa(Long id) {
        Optional<Tarefa> tarefa = repository.findById(id);

        if (tarefa.isPresent()) {
            tarefa.get().setFinalizado(true);
        }

        return repository.save(tarefa.get());
    }
}
