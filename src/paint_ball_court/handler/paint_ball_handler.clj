(ns paint-ball-court.handler.paint-ball-handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.util.response :as ring]
            [hiccup.middleware :refer [wrap-base-url]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [paint-ball-court.views.pages :as pages]
            [paint-ball-court.models.migration :as schema]
            [paint-ball-court.models.paint-ball-db :as db]
            [paint-ball-court.auth.auth :as auth]))

(defn init []
  (schema/migrate))

(defn destroy []
  (println "Goodbye"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "404 Page Not Found"))

(defroutes page-routes
  (GET "/" request
       (if-not (authenticated? request)(throw-unauthorized)(pages/index)))
  ;  (GET "/" request(pages/index))

  (GET "/courts-all" [] (pages/courts-all))

  (GET "/courts-edit/:id" [id] (pages/courts-edit id))

  (GET "/courts-new" [] (pages/courts-edit))

  (POST "/courts-save"
        [id name capacity image description price]
        (if (clojure.string/blank? id)
          (do
            (db/create-court name capacity image description price)
            (ring/redirect "/courts-all"))
          (do
            (db/update-court id name capacity image description price)
            (ring/redirect "/courts-all"))))

  (GET "/courts-delete/:id" [id]
       (do
         (db/delete-court id)
         (ring/redirect "/courts-all")))

  (GET "/reservations-all" [] (pages/reservations-all))

  (GET "/reservations-edit/:court_id/:id" [court_id id] (pages/reservations-edit court_id id))

  (GET "/reservations-new/:court_id" [court_id] (pages/reservations-edit court_id))

  (POST "/reservations-save"
        [id court_id date time name contact_number discount] ()
        (if (clojure.string/blank? id)
          (do
            (db/create-reservation court_id date time name contact_number discount)
            (ring/redirect "/reservations-all"))
          (do
            (db/update-reservation id court_id date time name contact_number discount)
            (ring/redirect "/reservations-all"))))

  (GET "/reservations-delete/:id" [id]
       (do
         (db/delete-reservation id)
         (ring/redirect "/reservations-all"))))

(def app
  (-> (routes page-routes app-routes)
      (handler/site)
      (wrap-base-url)
      (wrap-authorization auth/auth-backend)
      (wrap-authentication auth/auth-backend)))