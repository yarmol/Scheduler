Scheduler
---------

Scheduler is a web-based time management assistant. It can calculate and 
display hourly schedule on requested time period with entered tasks. 
Also it helps count free time and planning time, shows when some task
can be finished with a given time budget.

I wrote this application for self-education. The main purpose of this 
application is to demonstrate basic understanding OOP, Java-based and
related technologies like: HTML, JSP, Servlet, Spring MVC,
Spring Secure(for Spring MVC only), JPA, Hibernate, SQL, Maven, Git and
JUnit. Not to consider this project like finished, not all functionality
is developed and application has some [bugs](https://github.com/yvalera/Scheduler/issues) for now. I will be grateful to
get any warnings, criticism or advices about this educational application
on my email yvalera85@gmail.com or on project's [bug tracker](https://github.com/yvalera/Scheduler/issues).

The application requires servlet container (tested on Tomcat 8.0) and
relation database to launch. Now it configured to work with MySql at
database "scheduler" (must exist). Warning! Hibernate configured to
automatically creation or structure changing all the tables if it is
necessary. Table security will be created if it doesn't exist too. 