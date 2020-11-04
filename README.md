# Lucene Task
A java search engine based on the data `corpus/cran.all.1400`, user can choose Analyzer and Similarity to fininsh queries`corpus/cran.qry`. 
After finshing the queries, the engine will output a result file, user can use trec_eval tool to finish evaluation.

## Environment 
`Java 1.8` `Lucene 8.6.3`

## Compile and package
`mvn clean compile package`

## Build the index (you can skip this step, the indexs are already be built)
`java -jar target/lucenetask-1.0.jar index -a {Analyzer}`
-  (Analyzer can be Standard or English)
-  e.g. `java -jar target/lucenetask-1.0.jar index -a English`

# Start to query
`java -jar target/lucenetask-1.0.jar query -a {Analyzer} -s {Similarity}`
- (Analyzer can be Standard or English, Similarity can be BM25 or Classic)
- e.g. `java -jar target/lucenetask-1.0.jar query -a English -s BM25`  

## Evaluate
