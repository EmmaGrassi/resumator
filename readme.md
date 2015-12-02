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

## 3rd Party libs
[docx4j](https://github.com/plutext/docx4j) for exporting in HTML, PDF, DOCX

## Install Requirements
* JDK 8
* Maven 3.x

## Usage
1. Create a [config file](FIXME)
2. Start the application with:

```
java -jar resumator-${version}.jar -Dresumator.config=<path to the config file>
```

3. Open your browser at `http://localhost:8080/resumator`

## License
All the code and documentation in this repository are distributed under [MIT license](https://opensource.org/licenses/MIT).
