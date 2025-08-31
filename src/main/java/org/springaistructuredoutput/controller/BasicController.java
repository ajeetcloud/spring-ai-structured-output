package org.springaistructuredoutput.controller;

import org.springaistructuredoutput.model.MoviesRes;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    private final ChatClient chatClient;

    public BasicController(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools) {

        this.chatClient = chatClientBuilder
                .defaultSystem("Please prioritise context information for answering queries. Give short, concise and to the point answers.")
                .defaultToolCallbacks(tools)
                .build();
    }

    @GetMapping("/normal-response")
    public String getMovieText(@RequestParam String query) {
        PromptTemplate promptTemplate = new PromptTemplate(query);
        Prompt prompt = promptTemplate.create();
        String response = chatClient.prompt(prompt).call().content();

        return response;
    }

    @GetMapping("/structured-response")
    public MoviesRes getMovieStructured(@RequestParam String query) {
        PromptTemplate promptTemplate = new PromptTemplate(query);
        Prompt prompt = promptTemplate.create();
        MoviesRes moviesRes = chatClient.prompt(prompt)
                .call()
                .entity(MoviesRes.class);

        return moviesRes;
    }
}
