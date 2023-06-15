## howto

		minikube start --kubernetes-version=v1.19.16
		minikube addons enable ingress
		kc apply -f resource_with_ingress.yml
		kc get all
		kc get ingress
		kc port-forward service/foo-service :8080
		# curl localhost:????
		kc get ingress
		minikube tunnel
		# curl 0.0.0.0/foo
		kc delete -f resource_with_ingress.yml
		minikube delete

