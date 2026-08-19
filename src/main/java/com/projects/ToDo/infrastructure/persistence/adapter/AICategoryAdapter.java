package com.projects.ToDo.infrastructure.persistence.adapter;

import com.projects.ToDo.domain.model.Category;
import com.projects.ToDo.domain.port.AICategoryServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AICategoryAdapter implements AICategoryServicePort {

    private final ChatClient chatClient;

    public AICategoryAdapter(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public Category categorizeTask(String title) {

        String prompt = """
                Categorize this task title into one of the following categories:
                1 - Study
                2 - Shopping
                3 - Work
                4 - Personal
                5 - Other

                Task Title: "%s"

                Respond ONLY with the single digit number representing the best matching category. Do not include any other text or punctuation.
                """;

        String response = chatClient.prompt()
                .user(String.format(prompt, title))
                .call()
                .content();
                
        // Extract just the digit from the response
        String categoryIdStr = response != null ? response.trim().replaceAll("[^1-5]", "") : "5";
        if (categoryIdStr.isEmpty()) {
            categoryIdStr = "5"; // Default to Other
        }
        
        Long categoryId = Long.parseLong(categoryIdStr);
        String categoryName = switch (categoryId.intValue()) {
            case 1 -> "Study";
            case 2 -> "Shopping";
            case 3 -> "Work";
            case 4 -> "Personal";
            default -> "Other";
        };
        
        log.info("Task '{}' categorized as: {} ({})", title, categoryName, categoryId);

        return new Category(categoryId, categoryName);

    }
}
