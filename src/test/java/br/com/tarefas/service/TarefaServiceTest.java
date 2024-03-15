package br.com.tarefas.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.tarefas.entities.Departamento;
import br.com.tarefas.entities.Pessoa;
import br.com.tarefas.entities.Tarefa;
import br.com.tarefas.record.DepartamentoRecord;
import br.com.tarefas.record.TarefaRecord;
import br.com.tarefas.repository.DepartamentoRepository;
import br.com.tarefas.repository.TarefaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @Test
    public void testAdicionarTarefa() {
        DepartamentoRecord departamentoRecord = new DepartamentoRecord(1L, "");
        TarefaRecord tarefaRecord = new TarefaRecord(1L, "", "", LocalDate.now(), departamentoRecord, 1L, false);

        when(departamentoRepository.getById(any())).thenReturn(new Departamento());
        when(tarefaRepository.save(any())).thenReturn(new Tarefa());

        Tarefa addedTarefa = tarefaService.adicionarTarefa(tarefaRecord);

        assertNotNull(addedTarefa);
    }

    @Test
    public void testFinalizaTarefaNaoFinalizada() {
        Long tarefaId = 1L;
        Tarefa tarefa = new Tarefa();

        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any())).thenReturn(tarefa);

        Tarefa finalizedTarefa = tarefaService.finalizaTarefa(tarefaId);

        assertTrue(finalizedTarefa.isFinalizado());
    }

    @Test
    public void testFinalizaTarefaJaFinalizada() {
        Long tarefaId = 1L;
        Tarefa tarefa = new Tarefa();

        tarefa.setFinalizado(true);
        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));

        assertThrows(RuntimeException.class, () -> tarefaService.finalizaTarefa(tarefaId));
    }
    @Test
    public void testListarTarefasPendentes() {
        int maxTarefas = 5;
        List<Tarefa> tarefas = new ArrayList<>();

        when(tarefaRepository.findTarefasPendentesOrderByPrazo(maxTarefas)).thenReturn(tarefas);

        List<Tarefa> pendingTarefas = tarefaService.listarTarefasPendentes(maxTarefas);

        assertEquals(tarefas, pendingTarefas);
    }

    @Test
    public void testAlocarPessoaNaTarefaComMesmoDepartamento() {
        Long tarefaId = 1L;
        Long pessoaId = 1L;

        Tarefa tarefa = new Tarefa();
        Pessoa pessoa = new Pessoa();
        Departamento departamento = new Departamento();

        tarefa.setDepartamento(departamento);
        pessoa.setDepartamento(departamento);

        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        when(pessoaService.findById(pessoaId)).thenReturn(Optional.of(pessoa));

        assertDoesNotThrow(() -> tarefaService.alocarPessoaNaTarefa(tarefaId, pessoaId));
    }

    @Test
    public void testAlocarPessoaNaTarefaSemPessoa() {
        Long tarefaId = 1L;
        Long pessoaId = 1L;

        Tarefa tarefa = new Tarefa();

        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        when(pessoaService.findById(pessoaId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tarefaService.alocarPessoaNaTarefa(tarefaId, pessoaId));
    }

    @Test
    public void testAlocarPessoaNaTarefaComDepartamentoDiferente() {
        Long tarefaId = 1L;
        Long pessoaId = 1L;

        Tarefa tarefa = new Tarefa();
        Pessoa pessoa = new Pessoa();
        Departamento departamentoTarefa = new Departamento();
        Departamento departamentoPessoa = new Departamento();

        tarefa.setDepartamento(departamentoTarefa);
        pessoa.setDepartamento(departamentoPessoa);

        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        when(pessoaService.findById(pessoaId)).thenReturn(Optional.of(pessoa));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> tarefaService.alocarPessoaNaTarefa(tarefaId, pessoaId));
        assertEquals("A pessoa é de departamento diferente!", exception.getMessage());
    }
}