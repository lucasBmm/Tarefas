package br.com.tarefas.controller;

import br.com.tarefas.record.DepartamentoRecordData;
import br.com.tarefas.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService service;

    @GetMapping
    public ResponseEntity<List<DepartamentoRecordData>> listarDepartamentosComQuantidade() {
        List<DepartamentoRecordData> departamentos = service.listarDepartamentosComQuantidade();
        return new ResponseEntity<>(departamentos, HttpStatus.OK);
    }
}
