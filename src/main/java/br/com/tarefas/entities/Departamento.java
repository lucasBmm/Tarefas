package br.com.tarefas.entities;

import br.com.tarefas.record.DepartamentoRecord;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    public Departamento(DepartamentoRecord departamentoRecord) {
        this.titulo = departamentoRecord.titulo();
    }

    public Departamento(String titulo) {
        this.titulo = titulo;
    }
}
