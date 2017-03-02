(ns compojure-getting-started.nesting
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

;; Nesting routes
(defn inner-routes [id]
  (routes
    (GET "/profile" []
      (str  "Person: "id " profile"))
    (GET "/posts" []
      (str "Person: " id " posts"))))
(defroutes user-routes
           ; Using the context macro
           (context "/user/current" []
             (GET "/" [] "Hello user")
             (GET "/profile" [] "User profile")
             (GET "/posts" [] "User posts"))

           ; with path-params
           (context "/account/:id" [id]
             (GET "/" []
               (str "Hello " id))
             (GET "/profile" []
               (str  "Profile of: " id))
             (GET "/posts" []
               (str "Posts from: " id)))

           ; with routes defined separately
           (context "/person/:id" [id]
             (inner-routes id))
           )

(def app
  (wrap-defaults user-routes site-defaults))