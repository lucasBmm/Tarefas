package br.com.tarefas.entities;

import br.com.tarefas.record.TarefaRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tarefa {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private LocalDate prazo;

    @ManyToOne()
    @JoinColumn(name = "idDepartamento")
    private Departamento departamento;
    private Long duracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPessoa")
    private Pessoa pessoa;

    private boolean finalizado;

    public Tarefa(TarefaRecord tarefaRecord) {
        this.titulo = tarefaRecord.titulo();
        this.descricao = tarefaRecord.descricao();
        this.prazo = tarefaRecord.prazo();
        this.departamento = new Departamento(tarefaRecord.departamento());
        this.duracao = tarefaRecord.duracao();
        this.finalizado = false;
    }
}

