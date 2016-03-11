```
  ╔╦╗┬ ┬┌─┐  ╦═╗┌─┐┌─┐┬ ┬┌┬┐┌─┐┌┬┐┌─┐┬─┐TM
   ║ ├─┤├┤   ╠╦╝├┤ └─┐│ ││││├─┤ │ │ │├┬┘
   ╩ ┴ ┴└─┘  ╩╚═└─┘└─┘└─┘┴ ┴┴ ┴ ┴ └─┘┴└─
```

# resumator
A resumes' management tool written in Java developed for the Sytac resumes creation, storing, searching and exporting.

## Features
Creation, storing, searching and exporting of Sytac Resumes:
- web form for insertion
- storing of resumes into a SQL database (only PostgreSQL supported at the moment)
- exporting in HTML, PDF or DOCX

## Documentation

- [building from sources](https://github.com/sytac/resumator/blob/master/doc/building.md)
- [run the application](https://github.com/sytac/resumator/blob/master/doc/running.md)
- [configuration reference](https://github.com/sytac/resumator/blob/master/doc/configure.md)

## 3rd Party libs
[Apache POI](https://poi.apache.org/) for exporting in DOCX
[Lombok](https://projectlombok.org/) for generating boilerplate code

## Usage
```
mvn clean install; RESUMATOR_CONFIG=~/.resumator docker-compose -f ./docker/docker-compose-dev.yml up;
```

3. Open your browser at `http://localhost:8080/resumator`

## License
All the code and documentation in this repository are distributed under [MIT license](https://opensource.org/licenses/MIT).



## Layout

```
   Load balancer :9000
    |_______________|
            |
            |
       _____|____
     /           \
    /              \
Service :9090     UI:80   
    |
    |
  DB:5432

```
