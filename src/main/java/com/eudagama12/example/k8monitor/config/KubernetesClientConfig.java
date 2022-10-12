package com.eudagama12.example.k8monitor.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class KubernetesClientConfig {

    @Bean
    public CoreV1Api coreV1Api() {
        ApiClient client;
        try {
            // Loading KubeConfig from file-system: $HOME/.kube/config file
            client = Config.defaultClient();
        } catch (IOException e) {
            log.error("Failed to initialize KubeConfig: {}", e.getMessage());
            client = new ClientBuilder().build();
        }

        return new CoreV1Api(client);
    }

}
