# Busy Work!

Here's a repository that contains an example of some microservices providing
some "backend" services for a Web application. If you don't have PostgreSql
installed, then open up the `application.yml` and comment out the postgres
entries for the database and uncomment the H2 entries.

## Running some code

Open up three command lines and run `mvn spring-boot:run` in each of the three
subdirectories in this repository and in this order:

1. web-consumer
1. mq-microservice
1. restful-microservice

(This is only so that, if you make changes to the code and use Live Reload, the
broswer will reload when you make changes to the `web-consumer` project.)

Open your browser to [`http://localhost:10000`](http://localhost:10000) to see
the *Busy Work* application.

## Creating Busy Work

Now that you have a running UI and microservices, you can create a new task
using the left entry form. Depending on the type of task that you choose, you
will see:

* **None**: If all goes well, you'll get a COMPLETED task. Otherwise, you'll get
  a task in the ERROR state.
* **RESTful**: If all goes well, you'll get an IN_PROGRESS task. Otherwise,
  you'll get a task in the ERROR state.
* **Evented**: If all goes well, you'll get an IN_PROGRESS task. Otherwise,
  you'll get a task in the ERROR state.
* **Both**: If all goes well, you'll get an IN_PROGRESS task. Otherwise, you'll
  get a task in the ERROR state.

## Approving Busy Work

Now, open a browser to
[`http://localhost:10000/approvals`](http://localhost:10000/approvals) and click
the "Complete" button next to any of the incomplete tasks. This will move the
task in the microservices' domains to the COMPLETE status. This, in turn, will
inform the `web-consumer` application that the task has changed.
