package com.rayyau.eshop.eshop.api.gateway.controller;

import com.rayyau.eshop.eshop.api.gateway.dto.McpMessageRequest;
import com.rayyau.eshop.payment.library.annotation.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RequestMapping("mcp-chat")
@RestController
@Slf4j
public class McpChatController {
    private final ChatClient chatClient;

    private final SyncMcpToolCallbackProvider syncMcpToolCallbackProvider;

    public McpChatController(ChatClient.Builder chatClientBuilder,
            @Qualifier("my-mcp-server-callback-tool-provider") SyncMcpToolCallbackProvider syncMcpToolCallbackProvider) {
        this.chatClient = chatClientBuilder.build();
        this.syncMcpToolCallbackProvider = syncMcpToolCallbackProvider;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody McpMessageRequest request, @UserId Long userId) {
        log.info("user " + userId + " is chatting with mcp");
        log.info("show all tools: " + syncMcpToolCallbackProvider.getToolCallbacks());
        log.info("received message: " + request.getMessage());
        Optional<String> optResponseMessage = Optional.ofNullable(chatClient.prompt()
                .user(request.getMessage())
                .toolCallbacks(syncMcpToolCallbackProvider)
                .toolContext(Map.of("userId", userId))
                .call().content());
        if(optResponseMessage.isEmpty()) {
            return ResponseEntity.status(204).body(Map.of("mcpResponse", "AI returned no response"));
        }
        Map<String, String> responseMap = Map.of("mcpResponse", optResponseMessage.get());
        log.info("response message: " + optResponseMessage);
        return ResponseEntity.ok(responseMap);
    }
}

