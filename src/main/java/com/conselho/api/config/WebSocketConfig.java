package com.conselho.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig {

    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Registra os endpoints do WebSocket para a aplicação.
     *
     * Este método configura o ponto de conexão WebSocket para os clientes e permite o fallback utilizando SockJS
     * caso o WebSocket não seja suportado pelo cliente. A configuração também restringe os domínios que podem
     * estabelecer conexões, permitindo conexões apenas de "http://localhost:3000" no momento.
     *
     * - O endpoint "/ws" é onde os clientes irão se conectar via WebSocket.
     * - O fallback com SockJS garante que, caso o WebSocket não seja compatível com o cliente, uma conexão HTTP alternativa
     * será usada.
     *
     * @param registry O {@link StompEndpointRegistry} que permite registrar os endpoints do WebSocket.
     */

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
    }
}
