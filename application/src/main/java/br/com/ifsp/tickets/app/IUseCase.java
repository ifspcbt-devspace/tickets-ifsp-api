package br.com.ifsp.tickets.app;

public interface IUseCase<IN, OUT> {

     OUT execute(IN anIn);

}
