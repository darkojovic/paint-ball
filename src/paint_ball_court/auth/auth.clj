(ns paint-ball-court.auth.auth
  (:require
            [buddy.auth.backends.httpbasic :refer [http-basic-backend]]
            [paint-ball-court.models.paint-ball-db :as db]))

(defn authenticate-user
  [req {:keys [username password]}]
  (when-let
    [db-password (:password (db/find-user username))]
    (when (= password db-password)
      (keyword username))))

(def auth-backend
  (http-basic-backend
   {:realm  "paint-ball"
    :authfn authenticate-user}))