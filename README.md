# REST API example application

This is a rest api project of a news blog, where you can send, delete, update and see posts.
The project is also currently running on heroku:
https://spring-boot-hello123123214.herokuapp.com/

# improvements
  Swagger running on: https://spring-boot-hello123123214.herokuapp.com/swagger-ui.html </br>
  Bean validation fixed.  </br>
  Refactored controller. </br>
  Exception handler for 'MethodArgumentNotValidException' exceptions
  

# SPECS
  h2 in-memory db </br>
  basic auth

# documentation
  
## end-points
## create user
   ### POST /users
    curl --location --request GET 'https://spring-boot-hello123123214.herokuapp.com/users' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "username": "",
        "email": "",
        "password": ""
    }'
      
    (  after this you need to encode the email and password field to base64, like this: arthur@gmail.com:123456   into   YXJ0aHVyQGdtYWlsLmNvbToxMjM0NTY=   )
    (  you also need to send in every request header because the api is stateless ) -> send like this Authorization: Basic YXJ0aHVyQGdtYWlsLmNvbToxMjM0NTY=
   
   ## create post
  ###    POST /blog
         curl --location --request POST 'https://spring-boot-hello123123214.herokuapp.com/blog' \
        --header 'Content-Type: application/json' \
        --header 'Authorization: Basic YXJ0aHVyQGdtYWlsLmNvbToxMjM0NTY=' \
        --data-raw '{
            "title":"",
            "category":"",
            "content":"",
            "imageUrl":""
        }'
  
  
  ## delete post by id
  ### DELETE /blog/{id}   
  curl --location --request DELETE 'https://spring-boot-hello123123214.herokuapp.com/blog/{id}' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Basic YXJ0aHVyQGdtYWlsLmNvbToxMjM0NTY=' \
  --data-raw ''
  
  ## update post by id
  ### PUT /blog/{id}   
  curl --location --request PUT 'https://spring-boot-hello123123214.herokuapp.com/blog/{id}' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Basic YXJ0aHVyQGdtYWlsLmNvbToxMjM0NTY=' \
  --data-raw '{
      "title":"",
      "category":"",
      "content":"",
      "imageUrl":""
  }'
   
  ## get a page of posts
  ### GET /blog/   
  ### POSSIBILITIES: you can set ?page&size&sort and can also make a search by a specific field like title, category or username.
  ### examples:
  ### blog?qtd=&size=&sort=
  ### blog?username=something
  ### blog?title=something
  ### blog?category=something
  
  curl --location --request GET 'https://spring-boot-hello123123214.herokuapp.com/blog/' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Basic YXJ0aHVyQGdtYWlsLmNvbToxMjM0NTY=' \
  --data-raw ''
  
  
  ## send a comment 
  ### POST /blog/comments/{idOfPost}
  curl --location --request POST 'https://spring-boot-hello123123214.herokuapp.com/blog/comments/1' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Basic YXJ0aHVyQGdtYWlsLmNvbToxMjM0NTY=' \
  --data-raw '{
      "content": "new comment"
  }'
  
   ## delete a comment 
   ### DELETE /blog/comments/{idOfPost}?idOfComment=
  curl --location --request DELETE 'https://spring-boot-hello123123214.herokuapp.com/blog/comments/1?idOfComment=3' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Basic YXJ0aHVyQGdtYWlsLmNvbToxMjM0NTY=' \
  --data-raw ''

