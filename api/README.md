# API
## Database
### Run in Docker
```
docker run -p 5439:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=placebo -d postgres:9.6.1
```

## Endpoints
### Get drugs
Request method: GET<br>
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
