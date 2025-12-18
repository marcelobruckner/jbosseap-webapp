package br.bruckner.jakarta.hello;

import java.util.List;

public interface AlunoRepository {

    List<Aluno> listarTodos();

    Aluno salvar(Aluno aluno);
}
