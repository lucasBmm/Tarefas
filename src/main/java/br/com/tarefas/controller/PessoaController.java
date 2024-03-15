package br.com.tarefas.controller;

import br.com.tarefas.entities.Pessoa;
import br.com.tarefas.record.PessoaGastosRecord;
import br.com.tarefas.record.PessoaRecord;
import br.com.tarefas.record.PessoaRecordData;
import br.com.tarefas.record.TarefaRecord;
import br.com.tarefas.service.PessoaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping
    public ResponseEntity<List<PessoaRecordData>> listarPessoas() {
        List<PessoaRecordData> pessoas = service.listarPessoas();
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @GetMapping("/gastos")
    public ResponseEntity buscarPessoasPorNomeEPeriodo(
            @RequestParam String nome,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {

        try {
            List<PessoaGastosRecord> pessoas = service.buscarPessoasPorNomeEPeriodo(nome, dataInicio, dataFim);
            return new ResponseEntity<>(pessoas, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<PessoaRecord> adicionaPessoa(@Valid @RequestBody PessoaRecord pessoaRecord, UriComponentsBuilder uriBuilder) {
        Pessoa pessoa = service.criaPessoa(pessoaRecord);

        var uri = uriBuilder.path("/pessoas/{id}").buildAndExpand(pessoa.getId()).toUri();

        return ResponseEntity.created(uri).body(new PessoaRecord(pessoa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePessoa(@PathVariable Long id, @RequestBody PessoaRecord pessoa) {
        try {
            Pessoa updatedPessoa = service.updatePessoa(id, pessoa);
            return ResponseEntity.ok(new PessoaRecord(updatedPessoa));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePessoa(@PathVariable Long id) {
        try {
            service.deletaPessoa(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
