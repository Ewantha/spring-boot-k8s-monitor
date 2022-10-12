package com.eudagama12.example.k8monitor.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KubernetesClientService {
    @Value("${kubernetes.namespace}")
    private String kubernetesNamespace;

    @Value("${kubernetes.pod.nameRegex}")
    private String kubernetesPodNameRegex;

    private final CoreV1Api api;

    public KubernetesClientService(CoreV1Api api) {
        this.api = api;
    }

    @Scheduled(fixedDelayString = "10000")
    public void run() {
        try {
            log.info("Starting Scheduler");
            List<String> pods = getPodsByNamespace(kubernetesNamespace);

            List<String> customerPods = filterPodsByNameRegex(pods);

            log.info(customerPods.toString());
            log.info(customerPods.toString());
            pods.clear();
            customerPods.clear();
        } catch (ApiException e) {
            log.error("API Exception: {} ResponseBody: {} ", e.getMessage(), e.getResponseBody());
        }
    }

    @NotNull
    private List<String> filterPodsByNameRegex(List<String> pods) {
        return pods.stream()
                .filter(f -> f.matches(kubernetesPodNameRegex))
                .collect(Collectors.toList());
    }

    public List<String> getPodsByNamespace(String namespace) throws ApiException {
        log.debug("Get all pods for namespace: {}", namespace);

        V1PodList list =
                api.listNamespacedPod(namespace, null, null, null, null, null, null, null, null, null, null);

        //List required data
        return list.getItems()
                .stream()
                .map(s -> Objects.requireNonNull(s.getMetadata()).getName())    //Map pod name to list
                .collect(Collectors.toList());
    }
}
