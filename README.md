NTFC: No Target Function Classifier implementing the classifier trainer API of the athlete/trainer pattern. 

See ntfc.yml config file for metadata about the microservice. The same metadata can be accesses by calling /metadata of the running service. 

The Dockerfile to see which commands you need to run the service on a Linux machine with Java. 

Or you can just run the Docker container including everything necessary. 

Version change log:

- 0.0.1: resolving necessary maven dependencies and building basis from abstract basis
- 0.1.0: implemented word mover distance (WMD) NTFC
- 0.1.1: implemented basic additive model (BAM) NTFC without TFIDF normalization
- 0.2.0: implemented DAI probabilistic clustering
- 0.3.0: Updating /models, /model and /configuration endpoint
- 0.4.0: Implementing Athlete API and updating GUI
- 1.0.0: Finished and tested NTFC microservice
- 1.0.1: Merged AVLTree and Utility functions from API v 1.0.5 to support further development
- 1.1.0: Implemented explanations for all categorizations
- 1.1.1: Updated JavaScript Library to common core API with specific extensions