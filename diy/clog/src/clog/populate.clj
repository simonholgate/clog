(comment "This file populates the database. It can be run from the shell
  with lein run -m clog.populate
          ")

(ns clog.populate
  (:use clj-yaml.core
        korma.db 
        korma.core 
        clog.models))


(defn -main []
  (print "Populating database...") (flush)
  (insert authors (values (:authors (parse-string (slurp "./resources/fixtures.yml")))))
  (println " done"))