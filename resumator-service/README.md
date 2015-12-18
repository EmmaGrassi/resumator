# Resumator service

This module implements the backend capabilities required by the Resumator.
It mainly consists of an event store and a few REST APIs to access the various
aggregates.

## Architectural overview

The Resumator service employs [event surcing][evsrc] to manage state changes,
and [CQRS][cqrs] to create data flows.

Events are persisted in a database (currently only PostgreSQL is supported).

Note: make sure to be familiar with the [Domain Driven Design][DDD] terminology
to fully understand the Resumator documentation.

### Commands and events

Whenever a `POST`, `PUT` or `DELETE` request comes in, a REST controller creates
a Command that describes the operation. Then, the AggregateRoot that's interested
by such operation is submitted the Command to execute it.

Once the AggregateRoot has processed the Command, an Event is generated and published
on the local EventBus

### Events and persistence

Every Event that transits on the EventBus is captured by the EventRepository and
then persisted on the SQL database.

 ## Main technologies

 - Jersey: REST API management and Dependency Injection
 - MyBatis: SQL persistency
 - Guava: EventBus


[evsrc]: http://martinfowler.com/eaaDev/EventSourcing.html
[cqrs]:  http://martinfowler.com/bliki/CQRS.html
[ddd]:   https://en.wikipedia.org/wiki/Domain-driven_design