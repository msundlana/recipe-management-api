# Database

1. Open a terminal and navigate to the directory ` cd .\docker\database\ ` containing the Dockerfile.

2. Open the Dockerfile and update environmental variables
   `ENV POSTGRES_USER 
   ENV POSTGRES_PASSWORD ` 

3. Run the following command to build the Docker image:
   `docker build -t recipe-postgres .`

4. Once the image is built, run the following command to start a container using the image:
   `docker run -d -p 5432:5432 --name recipe-postgres recipe-postgres`
5. Optionally you can also start a container using image with docker_compose `docker-compose up -d` 
    
