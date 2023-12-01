# Web Data Integration Team 10 - Movies

## Project Overview

This project is about the data integration of movie records that originate from different sources.
Main goal is to find correspondences between schemata attributes, identifying entities that describe the same real-world object and fuse them together into one common dataset.
Four major steps have to be performed in order to finish the process:
1. Data gathering
2. Schema mapping and data translation
3. Identity resolution
4. Data fusion

**\*Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany\***  
The code is based on the provided software by the [IE 683 Web Data Integration](https://www.uni-mannheim.de/dws/teaching/course-details/courses-for-master-candidates/ie-670-web-data-integration/#c101835) course in the University of Mannheim. We have consent to apply extensions on their templates to make it compatible with our use case.

### Data gathering

Three datasets were chosen for the project that serve as potential candidates for the integration process.
All entries describe popular movies and contain common attributes like their title and release date.
In addition, we have to make sure that all datasets share some movie records that describe the same entity.
The table below shows all datasets w.r.t. their sources, formats, amount of attributes / entities.
The origin of the data is unique, as they were retrieved from known movie databases like [TMDB](https://www.themoviedb.org/?language=de) or encyclopedias such as [Wikipedia](https://www.wikipedia.org/).

| Dataset | Source | Format | # of entities | # of attributes |
| ------- | ------ | ------ | ------------- | --------------- |
| DBpedia | https://dbpedia.org/sparql | csv | 10.000 | 8 |
| TMDB | https://www.kaggle.com/datasets/disham993/9000-movies-dataset | csv | 9.847 | 9 |
| Hydra Movies | https://www.kaggle.com/datasets/kayscrapes/movie-dataset | csv | 3.886 | 12 |

### Schema mapping and data translation

Schema mapping is the part of the integration process, which deals with the identification of correspondences between two schemata.
Since not all datasets cover the same characteristics in a common format, we have to apply schema mapping techniques between the source schema and the target schema.
Our source schema represents the dataset while the target schema shows the final outcome of the data translation process and ideally of the data fusion part.

| Attribute name | Attribute type |
| -------------- | -------------- |
| movie_id | string |
| title | string |
| year | integer |
| director | string/list |
| producer | string/list |
| actor | string/list |
| summary | string |
| poster_url | string |
| rating | float |

The target schema includes six attributes, where three of them can be potential list attributes and two columns as numeric types.
Movie identifier are generated in an unique matter for each movie record.
In addition, we convert the release data of some datasets into the year format, as we are only interested on a sub-part of the date and simplifies the identity resolution process.
The transformation of the year attribute depends on its current format and whether they are numeric or not.
For additional data translation, we have to make sure that attributes like director have to be converted into a list attribute by looking into their separators in the string.

### Identity resolution

Identity resolution is concerned with the identification of two or more movie records, sharing the same real-world entity.
The biggest challenge is to deal with corner cases like duplicate movie titles or titles that share similar titles like [The Room (2003)](https://en.wikipedia.org/wiki/The_Room) and [Room (2015)](https://en.wikipedia.org/wiki/Room_(2015_film)).

In order to ensure a fault-free execution of the identity resoltion process, we need three components:
1. A gold standard, showing movie record pairs that are (not) matching
2. Comparators that serve as matching rules for linear combination / machine learning algorithms
3. Blocking methods to avoid unnecessary comparisons

### Data fusion

Data fusion is the last step and is responsible for applying fusion techniques to unify the records into one common collection of movie entities.
Similar to the former sub-part, we have to rely on three components:
1. A gold standard, that shows complete movie records w.r.t. target schema attributes and are covered in all three datasets
2. Evaluation rules to check for the equality of two records based on some characteristics (with minor deviations)
3. Several fusers to fuse the movie records with the corresponding attributes

The outcome of the data fusion process will be a unified dataset, which follows the target schema structure.
However, we omitted the summary and poster_url attributes at the end to ensure a consistent & complete merge of entities.

## How to use the project

### Requirements

Two main components are required for the execution of our code files:
1. Java JDK 8 or higher
2. Apache Maven

The main Maven dependency is the [WInte.r framework](https://github.com/olehmberg/winter) by the Data & Web Science group of the University of Mannheim.
This framework allows us to use multiple techniques relevant for the data integration process, such as similarity measures for identity resolution.

### Execution of the project

1. Download the project and import it as a Maven repository
2. Execute the linear combination / machine learning algorithm in the `ir`-package to generate the correspondence files between two datasets
3. Execute the data fusion main file in the `df`-package to fuse movie records and generate the fused file in a XML format.

