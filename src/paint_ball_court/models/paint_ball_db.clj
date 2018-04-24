(ns paint-ball-court.models.paint-ball-db
  (:require [clojure.java.jdbc :as sql]
            [clj-time.coerce :as time-coerce]))

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

(defn find-reservation [id]
  (sql/query db-spec
             ["SELECT * FROM RESERVATION WHERE id = ?" (parse-number id)]
             {:result-set-fn first}))

(defn update-reservation [id court_id date time name contact_number discount]
  (println "Update reservation " id)
  (try
    (sql/update! db-spec
                 :reservation
                 {:date           (time-coerce/to-sql-date date)
                  :time           (time-coerce/to-sql-time time)
                  :name           name
                  :contact_number contact_number
                  :discount       (if (clojure.string/blank? discount)
                                    nil
                                    (parse-number discount))
                  :total_price    (*
                                   (:price (find-court court_id))
                                   (if (clojure.string/blank? discount)
                                     1
                                     (* 0.01 (- 100 (parse-number discount)))))}
                 ["id = ?" (parse-number id)])
    (catch Exception e
      (.printStackTrace
        (.getNextException e)))))

(defn delete-reservation [id]
  (println "Delete reservation")
  (try
    (sql/delete! db-spec
                 :reservation
                 ["id = ?" (parse-number id)])
    (catch Exception e
      (.printStackTrace
        (.getNextException e)))))

(defn create-reservation [court_id date time name contact_number discount]
  (println "Create reservation" court_id date time name contact_number discount)
  (try
    (sql/insert! db-spec :reservation
                 {:court_id       (parse-number court_id)
                  :date           (time-coerce/to-sql-date date)
                  :time           (time-coerce/to-sql-time time)
                  :name           name
                  :contact_number contact_number
                  :discount       (if (clojure.string/blank? discount)
                                    nil
                                    (parse-number discount))
                  :total_price    (*
                                   (:price (find-court court_id))
                                   (if (clojure.string/blank? discount)
                                     1
                                     (* 0.01 (- 100 (parse-number discount)))))})
    (catch Exception e
      (.printStackTrace
        (.getNextException e)))))