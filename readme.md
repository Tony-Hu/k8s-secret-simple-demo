# How to store environment variable tokens in k8s secrets

## WHY? - Why using k8s secrets
The k8s secrets provide the way to avoid hard code token/password/API in the k8s deployment yaml file.
<hr>

## What? - What should be expected 
This demo Spring Boot application will try to fetch environment variables. If there isn't one, it will use Spring property file instead.<br>
If you run this app locally. When you access [localhost:8080](localhost:8080), you will see this:<br>
```
The name is: "A local user name"
The password is: "I'm a local password"
The base 64 decoded string is: "I'm a base 64 encoded string "
```

If you run this app in k8s, say you make ``kubectl port-forward <The pod name> 8081:8080 --namespace=default`` to mapping to port 8081. When you access [localhost:8081](localhost:8081), you will see this:
```
The name is: "A k8s user name"
The password is: "I'm a k8s password"
The base 64 decoded string is: "I'm a k8s base 64 string "
```
<hr>
    
## HOW? How to do this?
1. **Import credentials to ``kubectl``**\
    Create a yaml file called ``secret.yaml``(Or any name you prefer).<br> 
    It should be stored separately and not listed in the version control. Perhaps you need to add the file in ``.gitignore``.
    ```yaml
    apiVersion: v1
    kind: Secret
    metadata:
        name: test-secret # This name shall be unique, and it will be referenced in the deployment yaml.
    stringData: # For saving plain string. Formed by a list of key value pairs. The values are environment variable values.
        username: A k8s user name 
        password: I'm a k8s password
    data: # For saving base64 encoded string. The k8s secret will automatically decode and save the string
        base64EncodeString: SSdtIGEgazhzIGJhc2UgNjQgc3RyaW5nCg==
    ```
    Then, use ``kubectl create -f secret.yaml`` to create the secret in k8s cluster.

2. **Create/Modify k8s deployment**

    Then, we create/modify the deployment k8s configure file(As already listed in ``deployment/dummy-test.yml``).
    ```yaml
    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: dummy-test
    spec:
      selector:
        matchLabels:
          app: dummy-test
      template:
        metadata:
          labels:
            app: dummy-test
        spec:
          containers:
          - name: dummy-test
            image: index.docker.io/azraelzealous/k8stest:latest
            imagePullPolicy: Always
            ports:
            - containerPort: 8080
            env:
              - name: MY_USERNAME
                valueFrom: # Rather than use "value:" directly, use "valueFrom" to make a reference from k8s secret
                  secretKeyRef:
                    name: test-secret # Should exact the same as your secret.yaml -> metadata -> name
                    key: username # A key from data/stringData in your secret.yaml
              - name: MY_PASSWORD
                valueFrom:
                  secretKeyRef:
                    name: test-secret
                    key: password
              - name: BASE64_STRING
                valueFrom:
                  secretKeyRef:
                    name: test-secret
                    key: base64EncodeString
    ```
    And then, run it in pipe line or execute ``kubectl create -f development/dummy-test.yaml`` to create this deployment.


<hr>

#### Extra Info
[Reference1](https://kubernetes.io/docs/concepts/configuration/secret/#using-secrets-as-environment-variables)<br>
[Reference2](https://kubernetes.io/docs/tasks/inject-data-application/distribute-credentials-secure/)<br>