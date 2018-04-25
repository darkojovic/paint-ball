(defproject paint-ball-court "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license
  {:name "Eclipse Public License"
   :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
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
                 [cheshire "5.8.0"]]
  :main ^:skip-aot paint-ball-court.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})