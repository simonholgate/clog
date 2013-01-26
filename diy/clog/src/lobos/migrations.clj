(comment "This file defines the migrations for the Clog database.
          To run the migrations open the Clojure REPL and run the following code:
          (use 'lobos.core 'lobos.connectivity 'lobos.migration 'lobos.migrations)
          (open-global clogdb)
          (migrate)
          ")

(ns lobos.migrations
  ;; exclude some clojure built-in symbols so we can use the lobos' symbols
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  ;; use only defmigration macro from lobos
  (:use (lobos [migration :only [defmigration]]
          core
          connectivity
          schema))
  (:require [clojure.string :as str]
            [clojure.java.jdbc :as sql])
  (:import (java.net URI)))

;;; Defines the database for lobos migrations

;; (def db-uri (java.net.URI. (System/getenv "DATABASE_URL"))) 

;; (def user-and-password (string/split (.getUserInfo db-uri) #":")) 

;; (def clogdb {:classname "org.postgresql.Driver" 
;;          :subprotocol "postgresql" 
;;          :user (get user-and-password 0) 
;;          :password (get user-and-password 1) ; may be nil 
;;          :subname (if (= -1 (.getPort db-uri)) 
;;                     (format "//%s%s" (.getHost db-uri) (.getPath db-uri)) 
;;                     (format "//%s:%s%s" (.getHost db-uri) (.getPort 
;; db-uri) (.getPath db-uri)))})
(defn cloud-db
  "Generate the db map according to cloud environment when available."
  []
  (when (System/getenv "DATABASE_URL")
    (let [url (URI. (System/getenv "DATABASE_URL"))
          host (.getHost url)
          port (if (pos? (.getPort url)) (.getPort url) 5432)
          path (.getPath url)]
      (merge
       {:subname (str "//" host ":" port path)}
       (when-let [user-info (.getUserInfo url)]
         {:user (first (str/split user-info #":"))
          :password (second (str/split user-info #":"))})))))

(def clogdb
  (merge {:classname "org.postgresql.Driver"
          :subprotocol "postgresql"
          :subname "//localhost:5432/px"}
         (cloud-db)))

;; (def clogdb
;;      {:classname "org.postgresql.Driver"
;;       :subprotocol "postgresql"
;;       :user (System/getenv "DB_USER")
;;       :password (System/getenv "DB_PASSWORD")
;;       :subname (System/getenv "DATABASE_URL")
;;       :ssl true
;;       :sslfactory "org.postgresql.ssl.NonValidatingFactory"})

(defmigration add-authors-table
  ;; code be executed when migrating the schema "up" using "migrate"
  (up [] (create clogdb
           (table :authors (integer :id :primary-key )
             (varchar :username 100 :unique )
             (varchar :password 100 :not-null )
             (varchar :email 255))))
  ;; Code to be executed when migrating schema "down" using "rollback"
  (down [] (drop (table :authors ))))

(defmigration add-posts-table
  (up [] (create clogdb
           (table :posts (integer :id :primary-key )
             (varchar :title 250)
             (text :content )
             (boolean :status (default false))
             (timestamp :created (default (now)))
             (timestamp :published )
             (integer :author [:refer :authors :id] :not-null))))
  (down [] (drop (table :posts ))))

(defn -main []
  (print "Creating database structure...") (flush)
  (open-global clogdb)
  (migrate)
  (println " done"))