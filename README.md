## Hotels Data Merge

Simple application for merging hotel data from various sources.

### Stack

* Java 8
* Springboot 3

### Installation

Prerequisites: 
* Java

### Running the application

Simply run the `java -jar target/hotel-data-merge.jar` command to start the webserver.
The server will start listening on `http://localhost:8000`. 

### API's

## Load hotel data Api
 endpoint : http://localhost:8080/ascenda/loaddata

## Search Hotel by Id
 endpoint : http://localhost:8080/ascenda/hotelid/iJhz

## Search Hotel by destination Id
 endpoint : http://localhost:8080/ascenda/destid/5432



### Response format

The response is an array of JSON objects (Hotel object in the TypeScript source).
For property names I chose usually the simpler but still clear names with Hungarian notation. 

Properties: 
* **id** (string)
* **name** (string)
* **destinationId** (number)
* **description** (string)
* **bookingConditions** (string array)
* **amenities** (Object)
  * general (string array)
  * room (string array)
* **images** (Object)
  * amenities (Object array)
    * url (string)
    * caption (string)
  * rooms (Object array)
    * url (string)
    * caption (string)
  * site (Object array)
    * url (string)
    * caption (string)
* **location** (Object)
  * address (string)
  * city (string)
  * country (string)
  * latitude (string)
  * longitude (string)
  
### Merging techniques

The reasoning and explanation of merging the individual properties of the hotels: 
* id: no merging necessary
* name: the non-null and non empty value wins
* destinationId: no merging necessary
* description: the non-null and non empty value wins
* bookingConditions: the non-null and non empty value wins
* amenities: the non-null and non empty value wins
* images: the non-null and non empty value wins
* location: the non-null and non empty value wins


### Enhancement

** Have saparte daily schedular to load data from endpoints.
** Process large hotel dataset through Apache Spark.
** Have data validation check.
