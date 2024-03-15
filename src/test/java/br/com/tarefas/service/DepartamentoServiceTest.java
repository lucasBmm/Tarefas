package br.com.tarefas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import br.com.tarefas.entities.Departamento;
import br.com.tarefas.record.DepartamentoRecordData;
import br.com.tarefas.repository.DepartamentoRepository;
import br.com.tarefas.repository.PessoaRepository;
import br.com.tarefas.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private DepartamentoService departamentoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        Long departamentoId = 1L;
        Departamento expectedDepartamento = new Departamento(departamentoId, "Departamento");

        when(departamentoRepository.getById(departamentoId)).thenReturn(expectedDepartamento);

        Optional<Departamento> foundDepartamento = departamentoService.findById(departamentoId);

        assertEquals(expectedDepartamento, foundDepartamento.get());
    }

    @Test
    public void testListarDepartamentosComQuantidade() {
        Departamento departamento1 = new Departamento(1L, "Departmento 1");
        Departamento departamento2 = new Departamento(2L, "Departmento 2");

        when(departamentoRepository.findAll()).thenReturn(Arrays.asList(departamento1, departamento2));

        when(pessoaRepository.countByDepartamento(any())).thenReturn(2L); // Assuming 2 persons for each department
        when(tarefaRepository.countByDepartamento(any())).thenReturn(3L); // Assuming 3 tasks for each department

        List<DepartamentoRecordData> departamentoRecordDataList = departamentoService.listarDepartamentosComQuantidade();

        assertEquals(2, departamentoRecordDataList.size());
    }
}
