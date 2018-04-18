(ns paint-ball-court.handler.paint-ball-handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.util.response :as ring]
            [hiccup.middleware :refer [wrap-base-url]]))

(defn init []
  (println "Welcome"))

(defn destroy []
  (println "Goodbye"))

(defroutes app-routes
  (route/resources "/"))

(def app
  (-> (routes app-routes)
      (handler/site)
      (wrap-base-url)))