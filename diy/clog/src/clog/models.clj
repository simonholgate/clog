(ns clog.models
  (:use korma.db
        korma.core)
  (:require [clojure.string :as string])
  (:import (java.net URI)))

;; (comment from Google groups post: https://groups.google.com/forum/?fromgroups=#!searchin/sqlkorma/heroku/sqlkorma/KdwAU0KfiU0/VhyTlnkA5nYJ
(def db-uri (java.net.URI. (System/getenv "DATABASE_URL"))) 

(def user-and-password (string/split (.getUserInfo db-uri) #":")) 

(def clogdb {:classname "org.postgresql.Driver" 
         :subprotocol "postgresql" 
         :user (get user-and-password 0) 
         :password (get user-and-password 1) ; may be nil 
         :subname (if (= -1 (.getPort db-uri)) 
                    (format "//%s%s" (.getHost db-uri) (.getPath db-uri)) 
                    (format "//%s:%s%s" (.getHost db-uri) (.getPort 
db-uri) (.getPath db-uri)))}) 
)

;; (comment connection info from http://www.leonardoborges.com/writings/2012/09/10/clojure-leiningen-heroku-aot-compilation-gotchas/ and env variables from http://www.jisaacks.com/setup-cakephp-app-on-heroku)
;; (def clogdb
;;      {:classname "org.postgresql.Driver"
;;       :subprotocol "postgresql"
;;       :user (System/getenv "DB_USER")
;;       :password (System/getenv "DB_PASSWORD")
;;       :subname (System/getenv "DATABASE_URL")})

(defentity authors)
(defentity posts)
