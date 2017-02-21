(ns compojure-getting-started.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))
;; Getting Started
(defroutes app-routes
           (GET "/" [] "Hello World!!")

           ;; Routes In Detail
           (GET "/user/:id" [id]
             (str "<h1>Hello user " id "</h1>"))

           ; Matching the URI (with RegEx)
           (GET "/person/:id{[0-9]+5}" [id]
             (str "<h1>Person id: " id "</h1>"))

           ; Destructuring the request
           (GET "/user/:id{[0-9]+5}/account/:name" {{id :id name :name} :params}
             (str "<h1>id: " id "</h1><h2>name: " name "</h2>"))

           ; Combining Routes
           ; TODO
           (route/not-found "Not Found"))



(def app
  (wrap-defaults app-routes site-defaults))
