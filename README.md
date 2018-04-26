#PAINT-BALL COURT APP

This is an application designed for administration of a paintball court,  as a part of the assignment for the course Software Engineering Tools and Methodology on Master's studies - Software Engineering and Computer Sciences at the Faculty of Organization Sciences, University of Belgrade, Serbia.

Prerequisites:
* Leiningen 
* PSQL

Running:
Create empty psql db named "paintball"
use "lein run" in command line

Used libraries:

[org.clojure/clojure "1.8.0"]

[ring/ring-core "1.6.1"]

[ring/ring-defaults "0.3.0"]

[ring/ring-jetty-adapter "1.6.1"]

[ring-server "0.5.0"]

[hiccup "1.0.5"]

[compojure "1.6.0"]

[org.clojure/java.jdbc "0.6.1"]

[org.postgresql/postgresql "9.4-1201-jdbc41"]

[clj-time "0.14.2"]

[clj-http "3.8.0"]

[cheshire "5.8.0"]

[buddy/buddy-auth "2.0.0"]

Functionalities:

* view, edit, add and delete courts
* view, edit, add and delete reservations for each court
* applying disconts on reservations that are calculated on database save
* weather forecast that is obtained from openWeatherMap API
* song of the day video in embedded youtube player
* authentification: only logged in users can access the webapp

On app startup, the app checks if the psql database has been created or migrated. If not, the migration is done - tables are created and some sample data is added. This is defined in models.migration.clj. Entity operations are implemented in models.paint-ball-db.clj. 

The app calls the openWeatherMap web service to get the data about current weather in Belgrade, using clj-http library for http request and chesire library for parsing the response.

youTubePlayer.js is a script that loads a video from youTube and plays it on index page.

In paint-ball-handler.clj there are routes of the app. The app has three main parts: index page with weather forecast and video, pages for courts administration (courts-all, courts-edit) and pages for reservations administration (reservations-all, reservations-edit). Hiccup library was used for the web pages, and they are defined in view.pages.clj. All pages have a common structure (layout) which can be found in view/layout.clj.
