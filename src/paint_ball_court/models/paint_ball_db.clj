(ns paint-ball-court.models.paint-ball-db
  (:require [clojure.java.jdbc :as sql]))

(def db-spec "postgresql://localhost:5432/paintball")

(defn parse-number [string]
  (if
    (re-find #"^-?\d+\.?\d*$" string)
    (read-string string)))

(defn all-courts []
  (sql/query db-spec
             "SELECT * FROM COURT ORDER BY ID;"))

(defn find-court [id]
  (sql/query db-spec
             ["SELECT * FROM COURT WHERE id = ?" (parse-number id)]
             {:result-set-fn first}))

(defn update-court [id name capacity image description price]
  (println "Update court " id)
  (try
    (sql/update! db-spec
                 :court
                 {:name        name
                  :capacity    (parse-number capacity)
                  :image       image
                  :description description
                  :price       (parse-number price)}
                 ["id = ?" (parse-number id)])
    (catch Exception e
      (.printStackTrace
        (.getNextException e)))))

(defn delete-court [id]
  (println "Delete court")
  (try
    (sql/delete! db-spec
                 :court
                 ["id = ?" (parse-number id)])
    (catch Exception e
      (.printStackTrace
        (.getNextException e)))))

(defn create-court [name capacity image description price]
  (println "Create court")
  (try
    (sql/insert! db-spec :court
                 {:name        name
                  :capacity    (parse-number capacity)
                  :image       image
                  :description description
                  :price       (parse-number price)})
    (catch Exception e
      (.printStackTrace
        (.getNextException e)))))

(defn reservations-by-court [court_id]
  (sql/query db-spec
             ["SELECT * FROM RESERVATION WHERE court_id = ?" court_id]))