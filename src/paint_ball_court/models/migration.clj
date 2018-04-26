(ns paint-ball-court.models.migration
  (:require [clojure.java.jdbc :as sql]
            [paint-ball-court.models.paint-ball-db :as db]))

(defn migrated? []
  "Checks if db should be created"
  (->
   (sql/query db/db-spec
              [(str "select count(*) from information_schema.tables "
                    "where table_name='court'")])
   first :count pos?))

(defn migrate []
  (when (not (migrated?))
    (print "Creating database...") (flush)
    (try
      (sql/db-do-commands db/db-spec
                          (sql/create-table-ddl :court
                                                [[:id :serial "PRIMARY KEY"]
                                                 [:name :varchar "NOT NULL"]
                                                 [:capacity :integer "NOT NULL"]
                                                 [:description :varchar]
                                                 [:image :varchar]
                                                 [:price :decimal "NOT NULL"]]))
      (sql/insert! db/db-spec
                   :court
                   {:name        "Zvezdara"
                    :capacity    45
                    :description "Paintball teren čini nedovršena četvorospratna zgrada. Dimenzije terena su 30×45 i na jednom je nivou. Metalna burad, kamionske gume, betonski blokovi i limene table su prepreke za igru."
                    :image       "images/zvezdara.jpg"
                    :price       1800.0})
      (sql/insert! db/db-spec
                   :court
                   {:name        "Zemun"
                    :capacity    70
                    :description "Teren je dimenzija 140×80. Prepreke su drveće, oborena stabla, betonske prepreke i drveni koturovi. Ne postoji dodatno osvetljenje, pa je moguće igrati samo tokom dana."
                    :image       "images/zemun.jpg"
                    :price       2000.0})
      (sql/insert! db/db-spec
                   :court
                   {:name        "Borča"
                    :capacity    28
                    :description "Teren u Borči je dimenzija 50×32 i na jednom je nivou. Prepreke su isključivo drvene. Osvetljen je reflektorima velike snage, dok stakleni krov omogućava igranje i po dnevnom svetlu."
                    :image       "images/borca.jpeg"
                    :price       1600.0})
      (sql/db-do-commands db/db-spec
                          (sql/create-table-ddl :reservation
                                                [[:id :serial "PRIMARY KEY"]
                                                 [:date :date "NOT NULL"]
                                                 [:time :time "NOT NULL"]
                                                 [:court_id :serial "REFERENCES COURT(ID)"]
                                                 [:name :varchar "NOT NULL"]
                                                 [:contact_number :varchar]
                                                 [:discount :decimal]
                                                 [:total_price :decimal "NOT NULL"]]))
      (sql/db-do-commands db/db-spec
                          (sql/create-table-ddl :app_user
                                                [[:id :serial "PRIMARY KEY"]
                                                 [:username :varchar "NOT NULL"]
                                                 [:password :varchar "NOT NULL"]]))
      (sql/insert! db/db-spec
                   :app_user
                   {:username "admin"
                    :password "paintball2018"})
      (println " done")

      (catch Exception e
        (.printStackTrace
          (.getNextException e))))))