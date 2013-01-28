(comment "This file populates the database. It can be run from the shell
  with lein run -m 'lobos.populate
          ")

(ns lobos.populate
  (:use (clj-yaml [core])))


(defn -main []
  (print "Populating database...") (flush)
  (parse-string (slurp "./resources/fixtures.yml"))
  (println " done"))