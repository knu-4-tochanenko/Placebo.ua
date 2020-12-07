# API
## Run
### Requirements
- Docker 19.03.13
- Java 11.0.9.hs-adpt
### Launch database in Docker
```
docker run -p 5439:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=placebo -d postgres:9.6.1
```
The database will be running on port 5439
### Start server
```
./gradlew bootRun
```
The server will be running on port 8989

## Endpoints
### Get drugs
Request method: GET<br>
Request url:<br>
```
/drugs
```
Query params:<br>
- sort: Sort drugs by (type, name, price) asc or desc
- page: Page to return number
- size: Page to return size

Request body:
```
{
    "name": String
    "type": String
    "minPrice": Float
    "maxPrice": Float
}
```

#### Examples:
1. Normal flow<br>

Request url:
```
/drugs?sort=type&sort=name,desc&page=0&size=2
```
Request body:
```json
{
    "type": "HEART",
    "maxPrice": 100
}
```
Response code: 200<br>
Response body:
```json
{
    "content": [
        {
            "id": 5,
            "name": "Entocort",
            "type": "HEART",
            "description": "Drug that cures heart diseases",
            "price": 6.75,
            "storeUrl": "https://www.northwestpharmacy.com/product/entocort",
            "imageUrl": "https://pendopharm.com/wp-content/uploads/2016/04/entocort-capsules-352x345.jpg"
        },
        {
            "id": 1,
            "name": "Corlanor",
            "type": "HEART",
            "description": "Drug that cures heart diseases",
            "price": 10.55,
            "storeUrl": "https://www.northwestpharmacy.com/product/corlanor",
            "imageUrl": "https://1au3b422k9zdqzddw3my51gg-wpengine.netdna-ssl.com/wp-content/uploads/sites/7/2018/12/1374b6c633ab4d50968e28192aeb5d14-Corlanor-694x848.jpg"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "pageNumber": 0,
        "pageSize": 2,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 3,
    "totalElements": 5,
    "last": false,
    "numberOfElements": 2,
    "number": 0,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "size": 2,
    "first": true,
    "empty": false
}
```
2. Validation errors<br>
Request body:
```json
{
    "maxPrice": -2
}
```
Response code: 400<br>
Response body:
```json
{
    "errors": {
        "maxPrice": "must be greater than or equal to 0"
    }
}
```
3. Internal server error<br>
Request body:
```json
{
    "maxPrice": 2
}
```
Response code: 500<br>
Response body:
```json
{
    "message": "Something went wrong..."
}
```
### Get drug by id
Request method: GET<br>
Path variable:<br>
- id: Drug to return id

#### Examples:
1. Drug exists<br>

Request url:
```
/drugs/2
```
Response code: 200<br>
Response body:
```json
{
  "id": 2,
  "name": "Abilify",
  "type": "MENTAL",
  "description": "Drug that cures mental diseases",
  "price": 52.00,
  "storeUrl": "https://www.northwestpharmacy.com/product/abilify",
  "imageUrl": "https://d.newsweek.com/en/full/269301/09-09-abilify.jpg?w=1600&h=1200&q=88&f=2501af28b5bf35ecbbbd77ff77491aachttps://d.newsweek.com/en/full/269301/09-09-abilify.jpg?w=1600&h=1200&q=88&f=2501af28b5bf35ecbbbd77ff77491aac"
}
```
2. Drug does not exist<br>

Request url:
```
/drugs/6
```
Response code: 404<br>
Response body:
```json
{
   "message": "Drug with id 6 is not found"
}
```

### Get drug types
Request method: GET<br>
Request url:
```
/drugs/types
```
Response example:
```json
[
    "HEART",
    "MENTAL",
    "DIGESTIVE"
]
```
