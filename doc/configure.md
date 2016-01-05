## Configuration sources

When the Resumator Java service needs to read a configuration entry, it will inspect the following sources in sequential order until it finds something:

- the `System` properties
- the user configuration file
- the resumator default properties

Having the `System` properties as the first search scope allows for any property to be overridden at application startup time, passing `-Dresumator.....` to the `java` command.

### The user properties file

The Resumator Java service will look for user-supplied properties file at the following location:

```
${USER_HOME}/.resumator/config.properties
```

The location of the user properties file can be altered by supplying the following parameter at application startup:

```
-Dresumator.config=${path to properties file}
```

The above is the only property that can be set exclusively as a System property.

## Configuration reference

The following configuration entries are understood by the Resumator:

                            Key | Description | Sample value
--------------------------------|-------------|-------------
`resumator.http.context.path`   |             |
`resumator.http.uri`            |             |
`resumator.service.name`        |             |
`resumator.service.version`     |             |
`resumator.db.sql.dir`          |             |
`resumator.db.driver`           |             |
`resumator.db.url`              |             |
`resumator.db.user`             |             |
`resumator.db.password`         |             |
`resumator.config`              |             |
`resumator.sys.threadpool.size` |             |
`resumator.logs.events.tag`     |             |
