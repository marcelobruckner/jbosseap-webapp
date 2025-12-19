package br.bruckner.jakarta.hello;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("alunos")
public class AlunoResource {

	@Inject
	private AlunoService alunoService;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Aluno> list() {
		return alunoService.listarTodos();
	}

	// @POST
	// @Consumes({ MediaType.APPLICATION_JSON })
	// @Produces({ MediaType.APPLICATION_JSON })
	// public Response novoAluno(@Valid Aluno aluno){
	// 	Aluno novoAluno = alunoService.salvar(aluno);

	// 	return Response.status(Response.Status.CREATED).entity(novoAluno).build();
	// }

}