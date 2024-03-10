package br.com.ifsp.tickets.domain.shared;

public abstract class Identifier<T> extends ValueObject {

    public abstract T getValue();

}
