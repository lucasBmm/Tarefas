package br.com.tarefas.entities;

import br.com.tarefas.record.PessoaRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToOne()
    @JoinColumn(name = "idDepartamento")
    private Departamento departamento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    private List<Tarefa> tarefas;

    public Pessoa(PessoaRecord pessoaRecord) {
        this.nome = pessoaRecord.nome();
        this.departamento = new Departamento(pessoaRecord.departamento());
        this.tarefas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
}
