package br.com.tarefas.record;

import br.com.tarefas.entities.Departamento;

public record DepartamentoRecord(Long id, String titulo) {
    public DepartamentoRecord(Departamento departamento) {
        this(departamento.getId(), departamento.getTitulo());
    }
}

