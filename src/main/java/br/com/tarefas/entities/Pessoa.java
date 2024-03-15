package br.com.tarefas.entities;

import br.com.tarefas.record.PessoaRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
        this.tarefas = new ArrayList<>();
    }
}
