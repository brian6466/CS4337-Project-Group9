# CS4337-Project-Group9

This application is based on Medium, focusing on providing a platform for users to create, share, manage, and comment on articles. They can also follow authors to keep up-to-date with their articles. The project follows Spring Boot microservice architecture and aims to provide scalability and modularity, allowing for independent development and testing.

**Local Run**

To build all projects run `./gradew build`

To run a project do `cd {project-name}` and then `docker-compose up -build`


**Description**

**UserApi:**

FollowController: in this file we have all our endpoints to do the following: follow a user, unfollow a user, get followers for a user, get following for a user. This controller file calls the follower service file which contains all our business logic which then calls our follower repository file which is updated and making changes to our database for the relevant tables.

FollowerService: Contains business logic for our follower controller

UserController: in this file we have all our endpoints to do the following: get a user, get all users, create a user, update a user, delete a user, ban a user, unban a user. This controller file calls the user service with our business logic where then the user service will call our repository file to make changes to the relevant database tables.

UserService: Contains business logic for our user controller

**ContentApi:**

ArticleController: in this file we have all our endpoints to do the following: get an article, get all articles, create an article, delete an article, update a article, get all articles a user created. This controller file calls the article service which then calls the article repository to make database changes

ArticleService: Contains business logic for our article controller

CommentController: in this file we have all our endpoints to do the following: create  comment, delete a comment, update a comment, get all comments on a page. Like all controllers above this file calls a comment service with our business logic and then the service calls our repository file to make database changes.

CommentService: Contains business logic for our comment controller.