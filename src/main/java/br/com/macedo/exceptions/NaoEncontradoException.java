package br.com.macedo.exceptions;

import br.com.macedo.util.Mensagem;
import br.com.macedo.util.MensagemErro;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NaoEncontradoException extends NotFoundException {

    private final transient List<Mensagem> mensagens = new ArrayList<>();

    public NaoEncontradoException(String... messages) {
        List<MensagemErro> list = Arrays.stream(messages).
                map(MensagemErro::new).collect(Collectors.toList());
        this.mensagens.addAll(list);
    }

    public NaoEncontradoException(Mensagem... messages) {
        this.mensagens.addAll(Arrays.asList(messages));
    }

    public NaoEncontradoException() {
    }

    public void add(Mensagem message) {
        this.mensagens.add(message);
    }

    public List<Mensagem> getMessages() {
        return mensagens;
    }
}
