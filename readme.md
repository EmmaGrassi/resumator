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
[docx4j](https://github.com/plutext/docx4j) for exporting in HTML, PDF, DOCX

## Usage
1. Create a [config file](FIXME)
2. Start the application with:

```
java -jar resumator-${version}.jar -Dresumator.config=<path to the config file>
```

3. Open your browser at `http://localhost:8080/resumator`

## License
All the code and documentation in this repository are distributed under [MIT license](https://opensource.org/licenses/MIT).
