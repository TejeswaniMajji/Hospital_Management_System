
# Application Assessment Configuration

The following configurations will be used to run application assessment for the currently opened Java project.

### Project
- Location: /Users/nithineleti/Downloads/PROJECTS/Hospital Database Management System

### Environment
- [AppCAT](https://aka.ms/appcat-java) configuration:
    - target: azure-aks, azure-appservice, azure-container-apps, cloud-readiness
    - mode: source-only

If you want to make changes to the configuration, update the [assessment-config.yaml](./assessment-config.yaml) file. Refer to the instructions below for more details.

### The configurable [AppCAT](https://aka.ms/appcat-java) arguments
- target:
  - description: target technology or target Azure compute service.
  - valid values:
    - azure-aks: Best practices for deploying an app to Azure Kubernetes Service.
    - azure-appservice: Best practices for deploying an app to Azure App Service.
    - azure-container-apps: Best practices for deploying an app to Azure Container Apps.
    - cloud-readiness: General best practices for making an application Azure ready.
    - linux: General best practices for making an application Linux ready.
    - openjdk11: General best practices for running a Java 8 application with Java 11.
    - openjdk17: General best practices for running a Java 11 application with Java 17.
    - openjdk21: General best practices for running a Java 17 application with Java 21.
- mode:
  - description: analysis mode.
  - valid values: 
    - source-only: analyze source code only
    - full: analyze source code and list dependencies
