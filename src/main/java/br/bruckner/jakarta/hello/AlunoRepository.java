package br.bruckner.jakarta.hello;

import java.util.List;

public interface AlunoRepository {
    public List<Aluno> listarTodos();
    public Aluno salvar(Aluno aluno);
}
