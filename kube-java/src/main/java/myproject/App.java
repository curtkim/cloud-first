package myproject;

import com.pulumi.Context;
import com.pulumi.Pulumi;
import com.pulumi.core.Output;
import com.pulumi.kubernetes.apps.v1.Deployment;
import com.pulumi.kubernetes.apps.v1.DeploymentArgs;
import com.pulumi.kubernetes.apps.v1.inputs.DeploymentSpecArgs;
import com.pulumi.kubernetes.core.v1.inputs.ContainerArgs;
import com.pulumi.kubernetes.core.v1.inputs.ContainerPortArgs;
import com.pulumi.kubernetes.core.v1.inputs.PodSpecArgs;
import com.pulumi.kubernetes.core.v1.inputs.PodTemplateSpecArgs;
import com.pulumi.kubernetes.meta.v1.inputs.LabelSelectorArgs;
import com.pulumi.kubernetes.meta.v1.inputs.ObjectMetaArgs;

import java.util.Map;

public class App {
  public static void main(String[] args) {
    Pulumi.run((Context ctx) -> {

      var labels = Map.of("app", "nginx");
      var labelSelector = LabelSelectorArgs.builder()
          .matchLabels(labels)
          .build();
      var podTemplateSpec = PodTemplateSpecArgs.builder()
          .metadata(ObjectMetaArgs.builder()
              .labels(labels)
              .build())
          .spec(PodSpecArgs.builder()
              .containers(ContainerArgs.builder()
                  .name("nginx")
                  .image("nginx")
                  .ports(ContainerPortArgs.builder()
                      .containerPort(80)
                      .build())
                  .build())
              .build())
          .build();
      var deploymentSpecArgs = DeploymentSpecArgs.builder()
          .selector(labelSelector)
          .replicas(1)
          .template(podTemplateSpec)
          .build();

      var deployment = new Deployment("nginx", DeploymentArgs.builder()
          .spec(deploymentSpecArgs)
          .build());

      Output<String> name = deployment.metadata()
          .applyValue(m -> m.orElseThrow().name().orElse(""));

      ctx.export("name", name);
    });
  }
}
