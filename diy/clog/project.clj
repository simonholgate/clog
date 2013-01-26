(defproject clog "0.1.0-SNAPSHOT"
  :description "CLOG: The Clojure Blog for Heroku deployment. 
Use: export DATABASE_URL=postgresql://localhost:5432/clogdb"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :main clog.core
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [ring "1.0.1" ;;; Exclude the clojure, clj-stacktrace from ring dependency
                   :exclusions [org.clojure/clojure
                               clj-stacktrace]]
                 [net.cgrand/moustache "1.1.0"]
                 [lobos "1.0.0-SNAPSHOT"]
                 [korma "0.3.0-RC2"]
                 ;;[org.clojars.ccfontes/korma "0.3.0-beta12-pgssl"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [enlive "1.0.0"]
                 [postgresql "9.1-901.jdbc4"]
                 [clj-yaml "0.3.1"]])
