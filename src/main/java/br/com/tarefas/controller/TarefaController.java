package br.com.tarefas.controller;

import br.com.tarefas.entities.Tarefa;
import br.com.tarefas.record.TarefaRecord;
import br.com.tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("tarefas")
public class TarefaController {

    @Autowired
    private TarefaService service;

    @PostMapping
    public ResponseEntity adicionaTarefa(@Valid @RequestBody TarefaRecord tarefaRecord, UriComponentsBuilder uriBuilder) {
        Tarefa tarefa = service.adicionarTarefa(tarefaRecord);

        var uri = uriBuilder.path("/tarefas/{id}").buildAndExpand(tarefa.getId()).toUri();

        return ResponseEntity.created(uri).body(new TarefaRecord(tarefa));
    }

    @PutMapping("finalizar/{id}")
    public ResponseEntity finalizaTarefa(@PathVariable Long id) {
        try {
            Tarefa tarefaFinalizada = service.finalizaTarefa(id);
            return ResponseEntity.ok(new TarefaRecord(tarefaFinalizada));

        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("pendentes")
    public ResponseEntity listarTarefasPendentes(@RequestParam(defaultValue = "3") int maxTarefas) {
        try {
            List<Tarefa> pendentes = service.listarTarefasPendentes(maxTarefas);

            return ResponseEntity.ok(pendentes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("alocar/{id}")
    public ResponseEntity<?> alocarPessoa(@PathVariable Long id, @RequestParam Long pessoaId) {
        try {
            service.alocarPessoaNaTarefa(id, pessoaId);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
