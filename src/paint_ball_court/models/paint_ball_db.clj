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