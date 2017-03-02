(ns compojure-getting-started.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.coercions :refer [as-int]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

;; Getting Started
;(defroutes app-routes
;           (GET "/" [] "Hello World!!")
;
;           ;; Routes In Detail
;           (GET "/user/:id" [id]
;             (str "<h1>Hello user " id "</h1>"))
;
;           ; Matching the URI (with RegEx)
;           (GET "/person/:id{[0-9]+5}" [id]
;             (str "<h1>Person id: " id "</h1>"))
;
;           ; Destructuring the request
;           (GET "/user/:id{[0-9]+5}/account/:name" {{id :id name :name} :params}
;             (str "<h1>id: " id "</h1><h2>name: " name "</h2>"))
;
;           ; Combining Routes
;           ; TODO
;           (route/not-found "Not Found"))

;; Destructuring Syntax
(defroutes app-routes
           ;; Regular Clojure Destructuring
           ; entire request map
           (GET "/" request
             (str request))

           ; complex destructuring
           (GET "/user" {{:keys [user-id]} :session}
             (str "The current user is " user-id))

           ;; Compojure-specific Destructuring
           (GET "/user/:id" [id]
             (str "The current user is " id))

           ; with query-params
           (GET "/person/:id" [id greeting]
             (str "<h1>" greeting " user " id "</h1>"))

           ; skip unbound query-params (ex. :params {:x "foo", :y "bar", :z "baz", :w "qux"})
           (GET "/foobar" [x y z]
             (str x ", " y ", " z))

           ; bind unassinged query-params (ex. :params {:x "foo", :y "bar", :z "baz", :w "qux"})
           (GET "/foobar2" [x y & z]
             (str x ", " y ", " z))

           ; Request binding (with full request map)
           (GET "/foobar3" [x y :as r]
             (str x ", " y ", " r))

           ; Request binding (specific keys from the request map)
           (GET "/foobar4" [x y :as {u :uri rm :request-method}]
             (str "'x' is \"" x "\"\n"
                  "'y' is \"" y "\"\n"
                  "The request URI was \"" u "\"\n"
                  "The request method was \"" rm "\""))
           ; Parameter coercion
           (GET "/bar" [x :<< as-int]
             (str "x (" x ") has been coerced to int"))
           )

(def app
  (wrap-defaults app-routes site-defaults))
