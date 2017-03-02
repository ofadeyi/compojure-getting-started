(ns compojure-getting-started.destructuring
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.coercions :refer [as-int]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

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
             (str "x (" x ") has been coerced to int")))

(defroutes application-binding)

(def app
  (wrap-defaults app-routes site-defaults))
