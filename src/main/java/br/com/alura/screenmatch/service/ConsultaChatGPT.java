package br.com.alura.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService(System.getenv("OPENAI_APIKEY"));

        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")//modelo de compatibilidade
                .prompt("traduza para o português o texto: " + texto) //prompt de comando
                .maxTokens(1000)// total de retorno da resposta
                .temperature(0.7)//para atualizar a resposta caso tenha atualização no banco de dados inteno
                .build();

        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }
}