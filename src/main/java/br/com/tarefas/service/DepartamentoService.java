package br.com.tarefas.service;

import br.com.tarefas.entities.Departamento;
import br.com.tarefas.record.DepartamentoRecordData;
import br.com.tarefas.repository.DepartamentoRepository;
import br.com.tarefas.repository.PessoaRepository;
import br.com.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TarefaRepository tarefaRepository;


    public Optional<Departamento> findById(Long id) {
        return repository.findById(id);
    }

    public List<DepartamentoRecordData> listarDepartamentosComQuantidade() {
        List<Departamento> departamentos = repository.findAll();
        return departamentos.stream().map(departamento -> {
            long quantidadePessoas = pessoaRepository.countByDepartamento(departamento);
            long quantidadeTarefas = tarefaRepository.countByDepartamento(departamento);
            return new DepartamentoRecordData(departamento.getTitulo(), quantidadePessoas, quantidadeTarefas);
        }).collect(Collectors.toList());
    }
}
