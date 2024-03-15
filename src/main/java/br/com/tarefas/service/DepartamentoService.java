package br.com.tarefas.service;

import br.com.tarefas.entities.Departamento;
import br.com.tarefas.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository repository;


    public Departamento findById(Long id) {
        return repository.getById(id);
    }
}
