#NTFC
This repository contains a contribution of the PhD Thesis *Bootstrapping Explainable Text Categorization in Emergent Knowledge-Domains* by Tobias Eljasik-Swoboda submitted to the University of Hagen, Germany on November 30th, 2020. Date of oral examiniation: March 2nd, 2021. 

The NTFC (No Target Function Classifier) microservice is implementing the classifier trainer API of the athlete/trainer pattern. As the name suggests it is an unsupervised classifier that only requires word embeddings to perform text categorization.

See ntfc.yml config file for metadata about the microservice. The same metadata can be accesses by calling /metadata of the running service. 

The Dockerfile to see which commands you need to run the service on a Linux machine with Java. 

Or you can just run the Docker container including everything necessary. 

The word embedding files are read as UTF-8 text files in word2vec's text format or GloVe's text format. 
Word2Vec has a first row of two numbers. (first number = amount of words, second number = dimensions per word). GloVe does not have this line.
Example:

5 4

the 0.140257 0.161060 -0.091016 -0.165413 

of 0.099457 0.051021 -0.092173 -0.010298 
 
one 0.286791 0.182158 -0.277047 -0.102341 

zero 0.214225 0.109208 -0.376888 -0.122675 

and 0.105397 -0.001950 -0.088293 -0.172398  


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
- 1.1.2: Fixed bug in algorithm selection during self-evaluation
- 1.2.0: Changed data structures to maps to experiment with computation throughput after finishing all dissertation experiments