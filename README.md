# Websocket chat server
## Stack
* Spring Boot Web
* Spring Data JPA
* Spring Security
* Spring WebSocket
* JWT (JSON Web Token)
* Lombok
* MapStruct
***
## Description
```
Server side of a WebSocket chat application with JWT and a simple message broker.
```
***
## Endpoints
* `/api/v1/auth/register`   
User registration   
  Response body:    
  ```json
  {
    "username": "username",
    "password": "password"
  }
  ```
* `/api/v1/auth/register`   
Login   
  Response body:
  ```json
  {
    "username": "username",
    "password": "password"
  }
  ```   
* `/api/v1/auth/register`   
  Refresh Token
* `/api/v1/messages/all`    
Get Messages (With pagination)
***
## Websocket
* Endpoint `/chat`  
  Destination `/app/message.send`   
Subscribe `/topic/messages`     
Payload:
  ```json
  {
    "id": 1,
    "content": "some message"
  }
  ```