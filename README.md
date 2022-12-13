# User Service

## This service lets you list, search by email, create and delete users
Any user has id, name, username, email and phone. The username and email should be unique.

### Running service

- cd user
- docker-compose up -d so mysql is up and running
- run services with gradle: './gradlew bootRun'

### Testing service

Create user
```
curl --location --request POST 'http://localhost:9000/users' \
--header 'Content-Type: application/json' \
--data-raw '{
	"email": "gogo@lolo.cl", 
	"name": "Foo", 
	"username": "gogosi", 
	"phone": "134134134"
}'
```

List all users:
```
curl --location --request GET 'http://localhost:9000/users' \
--header 'Content-Type: application/json'
```

Search by email:
```
curl --location --request GET 'http://localhost:9000/users/gogo@lolo.cl' \
--header 'Content-Type: application/json'
```

Delete user:
```
curl --location --request DELETE 'http://localhost:9000/users/5' \
--header 'Content-Type: application/json'
```

Count items of customer with Id:

```
curl --location --request POST 'http://localhost:9000/external/1-9' \
--header 'Content-Type: application/json' 
```
