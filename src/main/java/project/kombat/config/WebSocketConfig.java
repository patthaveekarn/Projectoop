package project.kombat.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  // Enable a broker for topics
        config.setApplicationDestinationPrefixes("/app");  // Messages that start with "/app" are routed to @MessageMapping
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")  // Register the "/ws" endpoint for WebSocket connections
                .setAllowedOrigins("http://localhost:3000")  // Allow connections from your Next.js frontend
                .withSockJS();  // Fallback options for browsers that don't support WebSocket
    }
}