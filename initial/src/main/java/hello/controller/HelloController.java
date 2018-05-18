package hello.controller;

import hello.model.NumberEnvelope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
public class HelloController {

//    Eu passei um argumento por valor e as
//    alterações feitas neste argumento também ocorrem na variável original.
//
//    Isso ocorre porque uma variável de tipo complexo contém uma referência ao objeto.
//    A palavra "referência" aqui tem outro sentido. Podemos dizer que referência, neste contexto, é o endereço de memória onde o objeto reside de fato.
//    Então a variável contém na verdade um endereço de memória, e se formos até este endereço vamos encontrar lá o nosso objeto.
    @GetMapping("/testBasic")
    public String basicTest(){
        NumberEnvelope numberEnvelope = new NumberEnvelope(10);
        setTest(numberEnvelope);
        return numberEnvelope.getNumber().toString();
    }

    public void setTest(NumberEnvelope numberEnvelope){
        numberEnvelope.setNumber(666);
    }

    @RequestMapping("/")
    public String index() {
        return "Olá, spring boot";
    }

    @RequestMapping("/future")
    public String testCompletableFuture(){
        try {
            this.calculateAsync();
           String teste = this.calculateAsync().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

    public Future<String> calculateAsynUsingLambda() throws InterruptedException {
        CompletableFuture<String> future
                = CompletableFuture.supplyAsync(() -> {
                    return "Hello";
                });

        return future;
    }
    
}
