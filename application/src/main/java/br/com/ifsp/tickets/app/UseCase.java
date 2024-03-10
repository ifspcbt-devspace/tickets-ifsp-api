package br.com.ifsp.tickets.app;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIn);

}
