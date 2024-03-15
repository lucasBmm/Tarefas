package br.com.tarefas.controller;

import br.com.tarefas.entities.Pessoa;
import br.com.tarefas.entities.Tarefa;
import br.com.tarefas.record.PessoaRecord;
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

    @PutMapping
    @RequestMapping("finalizar/{id}")
    public ResponseEntity finalizaTarefa(@PathVariable Long id) {
        Tarefa tarefaFinalizada = service.finalizaTarefa(id);

        return ResponseEntity.ok(new TarefaRecord(tarefaFinalizada));
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTarefasPendentes(@RequestParam(defaultValue = "3") int maxTarefas) {
        try {
            // Call the service method to fetch pending tasks with earliest deadlines
            List<Tarefa> pendentes = service.listarTarefasPendentes(maxTarefas);

            // Return the list of pending tasks in the response body
            return ResponseEntity.ok(pendentes);
        } catch (Exception e) {
            // Handle potential errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
