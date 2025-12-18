package br.bruckner.jakarta.hello;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AlunoRepositoryImpl implements AlunoRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Aluno> listarTodos() {
        return em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
    }

    @Override
    public Aluno salvar(Aluno aluno) {
        em.persist(aluno);
        return aluno;
    }
}
