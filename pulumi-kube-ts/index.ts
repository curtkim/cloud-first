import * as k8s from "@pulumi/kubernetes";
import * as kx from "@pulumi/kubernetesx";

const commonEnvVars = [
  {name: "SPRING_PROFILES_ACTIVE", value: "alpha"}
];

const appLabels = {app: "nginx"};

const deployment = new k8s.apps.v1.Deployment("nginx", {
  spec: {
    selector: {matchLabels: appLabels},
    replicas: 1,
    template: {
      metadata: {labels: appLabels},
      spec: {
        containers: [
          {
            name: "nginx",
            image: "nginx",
            imagePullPolicy: "IfNotPresent",
            livenessProbe: {
              httpGet: {path: "/", port: 80}
            },
            readinessProbe: {
              httpGet: {path: "/", port: 80}
            },
            env: commonEnvVars
          }
        ]
      }
    }
  }
});

export const name = deployment.metadata.name;
