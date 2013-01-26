(ns clog.core
  (:use ring.adapter.jetty
        ring.middleware.resource
        ring.middleware.reload
        ring.middleware.file
        ring.middleware.params
        ring.util.response
        ring.middleware.session
        ring.middleware.session.cookie
        net.cgrand.moustache
        clog.controller))
 
;;; A simple handler to show send some response to the client.
;; (defn index
;;   [req]
;;   (response "Welcome, to Clog - A Blog Engine written in Clojure"))
 
;; Routes definition
(def routes
  (app
   (wrap-params)
   (wrap-file "resources/public")
   (wrap-session {:cookie-name "clog-session" :store (cookie-store)})
   ["login"]  (delegate login)
   ["admin"] (delegate admin)
   ["logout"] (delegate logout)
   [""] (delegate  index)
   [id] (delegate post id)))
 
;;; start function for starting jetty
;; (defonce server (run-jetty #'routes {:port 9000 :join? false}))

;; (defn -main []
;;   (.start server))

(defn start [port host]
  (run-jetty #'routes {:port port :join? false :host host}))

(defn -main []
  (let [mode (keyword (or (first m) :dev))
        port (Integer/parseInt 
              (or (System/getenv "OPENSHIFT_INTERNAL_PORT") "8080"))
        host (get (System/getenv) "OPENSHIFT_INTERNAL_IP")]
    (start port host)))