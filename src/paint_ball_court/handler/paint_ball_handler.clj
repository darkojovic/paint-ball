(ns paint-ball-court.handler.paint-ball-handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.util.response :as ring]
            [hiccup.middleware :refer [wrap-base-url]]
            [paint-ball-court.views.pages :as pages]
            [paint-ball-court.models.migration :as schema]
            [paint-ball-court.models.paint-ball-db :as db]))

(defn init []
  (schema/migrate))

(defn destroy []
  (println "Goodbye"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "404 Page Not Found"))

(defroutes page-routes
  (GET "/" [] (pages/index))
  (GET "/courts-all" [] (pages/courts-all)))

(def app
  (-> (routes page-routes app-routes)
      (handler/site)
      (wrap-base-url)))