# Resumator domain

The development of the Resumator follows the principles of Domain Driven Design. This document details the domain that
the Resumator is using as its conceptual basis.

```

+-----------+ discovers  +-----------+
| Recruiter +----------->| Candidate |
+----+------+ 1        * +----+------+
     |                      1 |
     | recruits     +---------+
     |              | becomes
     v              |
+----------+ 1      |
| Employee |<-------+
+-+------+-+
  |     0| has +------------+
  |      +---->| Experience |
  |          * +------------+
  |
  |
  |
  |

```