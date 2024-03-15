package br.com.tarefas.record;

import java.time.LocalDate;

public record PessoaGastosRecord(String nome, LocalDate dataInicio, LocalDate dataFim, double mediaHorasGastas) {}